package com.mrozon.feature_auth.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.*
import com.mrozon.core_api.security.SecurityTokenService
import com.mrozon.utils.network.Result.Companion.loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import com.mrozon.utils.network.Result.Companion.error
import com.mrozon.utils.network.Result.Companion.success
import kotlinx.coroutines.test.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserAuthRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: HealthDiaryService

    @Mock
    lateinit var dao: HealthDiaryDao

    @Mock
    lateinit var securityTokenService: SecurityTokenService

    lateinit var repository: UserAuthRepository

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Before
    fun setUp() {
        val source = UserAuthRemoteDataSource(apiService)
        repository = UserAuthRepositoryImpl(source, dao, securityTokenService)
    }

    @Test
    fun `register user success`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val email = "test@mail.ru"
        val password = "password1"
        val request = RegisterRequest(email = email, first_name = "",
            last_name = "",username = email, password = password)
        val response = RegisterResponse(email=email, id = 1, first_name =  "",
            last_name = "", last_login = "", username = email )
        val expected = listOf(
            loading(),
            success(response.toUser())
        )

        `when`(apiService.registerUser(request)).thenReturn(
            Response.success(response)
        )

        val actual = repository.registerUser(response.toUser(),password)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `register user failed`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val error = "Bla-Bla!"
        val email = "test@mail.ru"
        val password = "password1"
        val request = RegisterRequest(email = email, first_name = "",
            last_name = "",username = email, password = password)
        val response = RegisterResponse(email=email, id = 1, first_name =  "",
            last_name = "", last_login = "", username = email )
        val expected = listOf(
            loading(),
            error("Network error:\n $error",null)
        )

        `when`(apiService.registerUser(request)).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )

        val actual = repository.registerUser(response.toUser(),password)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `login user success`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val username = "vasya@mail.ru"
        val password = "strong_password"
        val request = LoginRequest(username,password)
        val response = LoginResponse(email = username, first_name = "",
            last_name = "", token = "aaaaa", user_id = 101)

        val expected = listOf(
            loading(),
            success(response.toUser())
        )

        `when`(apiService.loginUser(request)).thenReturn(
            Response.success(response)
        )

        val actual = repository.loginUser(username,password)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `logged user added in db`() = coroutinesTestRule.testDispatcher.runBlockingTest {

        val username = "vasya@mail.ru"
        val password = "strong_password"
        val request = LoginRequest(username,password)
        val response = LoginResponse(email = username, first_name = "first_name",
            last_name = "last_name", token = "aaaaa", user_id = 101)

        `when`(apiService.loginUser(request)).thenReturn(
            Response.success(response)
        )

        repository.loginUser(username,password).toList()

        verify(dao).insertUser(response.toUserDb())
    }

    @Test
    fun `login user failed`() = coroutinesTestRule.testDispatcher.runBlockingTest {
        val error = "Bla-Bla!"
        val username = "vasya@mail.ru"
        val password = "strong_password"
        val request = LoginRequest(username,password)

        val expected = listOf(
            loading(),
            error("Network error:\n $error",null)
        )

        `when`(apiService.loginUser(request)).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )

        val actual = repository.loginUser(username,password)

        assertEquals(
            expected,
            actual.toList()
        )
    }



}

@ExperimentalCoroutinesApi
class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher() {

    private val testCoroutinesScope = TestCoroutineScope(testDispatcher)

//    override fun starting(description: Description?) {
//        super.starting(description)
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    override fun finished(description: Description?) {
//        super.finished(description)
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }

    override fun apply(base: Statement, description: Description?) = object : Statement() {
        override fun evaluate() {
            Dispatchers.setMain(testDispatcher)
            base.evaluate()
            Dispatchers.resetMain()
            testCoroutinesScope.cleanupTestCoroutines()
        }
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) {
        testCoroutinesScope.runBlockingTest{
            block()
        }
    }
}