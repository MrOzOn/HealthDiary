package com.mrozon.feature_person.data

import com.mrozon.core_api.db.HealthDiaryDao
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.mapper.PersonToPersonDbMapper
import com.mrozon.core_api.network.HealthDiaryService
import com.mrozon.core_api.network.model.*
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.utils.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
class PersonRepositoryImplTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: HealthDiaryService

    @Mock
    lateinit var dao: HealthDiaryDao

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    lateinit var repository: PersonRepository

    @Before
    fun setUp() {
        val source = PersonRemoteDataSource(apiService, dao)
        repository = PersonRepositoryImpl(dao, source, PersonToPersonDbMapper())
    }

    @Test
    fun `get persons success`() = coroutinesTestRule.runBlockingTest {
        val date = "2000-10-24"
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        val personDb = PersonToPersonDbMapper().map(person)!!
        Mockito.`when`(apiService.getPersons()).thenReturn(
            Response.success(listOf(response))
        )
        Mockito.`when`(dao.getPersons()).thenReturn(
            flowOf(listOf(personDb))
        )
        val expected = listOf(
            Result.loading(),
            Result.success(listOf(person)),
            Result.success(listOf(person))
        )

        val actual = repository.getPersons()

        assertEquals(
            expected,
            actual.toList()
        )
    }


    @Test
    fun `get persons network down success`() = coroutinesTestRule.runBlockingTest {
        val error = "bla-bla"
        val date = "2000-10-24"
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        val personDb = PersonToPersonDbMapper().map(person)!!
        Mockito.`when`(apiService.getPersons()).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )
        Mockito.`when`(dao.getPersons()).thenReturn(
            flowOf(listOf(personDb))
        )
        val expected = listOf(
            Result.loading(),
            Result.success(listOf(person)),
            Result.error("Network error:\n $error",null)
        )

        val actual = repository.getPersons()

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `refresh persons success`() = coroutinesTestRule.runBlockingTest {
        val date = "2000-10-24"
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        val personDb = PersonToPersonDbMapper().map(person)!!
        Mockito.`when`(apiService.getPersons()).thenReturn(
            Response.success(listOf(response))
        )
        Mockito.`when`(dao.getPersons()).thenReturn(
            flowOf(listOf(personDb))
        )
        val expected = listOf(
            Result.loading(),
            Result.success(listOf(person))
        )

        val actual = repository.refreshPersons()

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `refresh persons network down success`() = coroutinesTestRule.runBlockingTest {
        val error = "bla-bla"
        val date = "2000-10-24"
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        val personDb = PersonToPersonDbMapper().map(person)!!
        Mockito.`when`(apiService.getPersons()).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )
        Mockito.`when`(dao.getPersons()).thenReturn(
            flowOf(listOf(personDb))
        )
        val expected = listOf(
            Result.loading(),
            Result.error("Network error:\n $error",null)
        )

        val actual = repository.refreshPersons()

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `add person success`() = coroutinesTestRule.runBlockingTest {
        val date = "2000-10-24"
        val request = PersonRequest(born = date, gender = true, name ="")
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        Mockito.`when`(apiService.addPerson(request)).thenReturn(
            Response.success(response)
        )
        val expected = listOf(
            Result.loading(),
            Result.success(person)
        )

        val actual = repository.addPerson(person)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `add person failed`() = coroutinesTestRule.runBlockingTest {
        val date = "2000-10-24"
        val error = "bla-bla"
        val request = PersonRequest(born = date, gender = true, name ="")
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        Mockito.`when`(apiService.addPerson(request)).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )
        val expected = listOf(
            Result.loading(),
            Result.error("Network error:\n $error",null)
        )

        val actual = repository.addPerson(person)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `get person success`() = coroutinesTestRule.runBlockingTest {
        val date = "2000-10-24"
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        val person = response.toPerson()
        val personDb = PersonToPersonDbMapper().map(person)!!
        Mockito.`when`(dao.getPerson(Mockito.anyLong())).thenReturn(
            personDb
        )
        val expected = listOf(
            Result.loading(),
            Result.success(person)
        )

        val actual = repository.getPerson(1)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `delete person success`() = coroutinesTestRule.runBlockingTest {
        Mockito.`when`(apiService.deletePerson(Mockito.anyString())).thenReturn(
            Response.success(Unit)
        )
        val expected = listOf(
            Result.loading(),
            Result.success(null)
        )

        val actual = repository.deletePerson(1)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `delete person failed`() = coroutinesTestRule.runBlockingTest {
        val error = "bla-bla"
        Mockito.`when`(apiService.deletePerson(Mockito.anyString())).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )
        val expected = listOf(
            Result.loading(),
            Result.error("Network error:\n $error",null)
        )

        val actual = repository.deletePerson(1)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `share person success`() = coroutinesTestRule.runBlockingTest {
        val id = 100
        val name = "aaaaaa"
        val date = "2014-11-11"
        val request = SharePersonRequest(patient_id = id, username = name)
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        Mockito.`when`(apiService.sharePerson(request)).thenReturn(
            Response.success(response)
        )
        val expected = listOf(
            Result.loading(),
            Result.success(null)
        )

        val actual = repository.sharePerson(id.toLong(),name)

        assertEquals(
            expected,
            actual.toList()
        )
    }

    @Test
    fun `share person failed`() = coroutinesTestRule.runBlockingTest {
        val error = "bla-bla"
        val id = 100
        val name = "aaaaaa"
        val date = "2014-11-11"
        val request = SharePersonRequest(patient_id = id, username = name)
        val response = PersonResponse(avatar = "", born = date, created_date = date,
            gender = true, id=100, name = "", owners = listOf())
        Mockito.`when`(apiService.sharePerson(request)).thenReturn(
            Response.error(402, ResponseBody.create(MediaType.get("text/plain"),"[$error]"))
        )
        val expected = listOf(
            Result.loading(),
            Result.error("Network error:\n $error",null)
        )

        val actual = repository.sharePerson(id.toLong(),name)

        assertEquals(
            expected,
            actual.toList()
        )
    }




}


@ExperimentalCoroutinesApi
class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher() {

    private val testCoroutinesScope = TestCoroutineScope(testDispatcher)

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