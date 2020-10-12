package com.mrozon.feature_auth.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_auth.data.CoroutineTestRule
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.utils.network.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.coroutines.CoroutineContext


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class RegistrationFragmentViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: UserAuthRepository

    @Mock
    lateinit var observer: Observer<Result<User>?>

    @Mock
    lateinit var validateObserver: Observer<ValidateDataError>

    lateinit var viewModel: RegistrationFragmentViewModel

    @Before
    fun setUp() {
        viewModel = RegistrationFragmentViewModel(repository, TestCoroutineProvider())
            .apply {
                registeredUser.observeForever(observer)
            }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `register user success`() = coroutinesTestRule.runBlockingTest {
        val password = "password1"
        val email = "vasya@mail.ru"
        val firstName = "first"
        val lastName = "last"
        viewModel.passwordChannel.offer(password)
        viewModel.emailChannel.offer(email)
        viewModel.firstNameChannel.offer(firstName)
        viewModel.lastNameChannel.offer(lastName)
        val user = User(email = email, firstname = firstName, lastname = lastName)

        Mockito.`when`(repository.registerUser(Mockito.any<User>()?:user, Mockito.anyString())).thenReturn(
            flowOf(Result.loading(), Result.success(user))
        )

        viewModel.registerUser()

        Mockito.verify(observer).onChanged(Result.loading())
        Mockito.verify(observer).onChanged(Result.success(user))
    }

    @Test
    fun `register user failed`() = coroutinesTestRule.runBlockingTest {
        val password = "password1"
        val email = "vasya@mail.ru"
        val firstName = "first"
        val lastName = "last"
        val error = "Bla-bla!"
        viewModel.passwordChannel.offer(password)
        viewModel.emailChannel.offer(email)
        viewModel.firstNameChannel.offer(firstName)
        viewModel.lastNameChannel.offer(lastName)
        val user = User(email = email, firstname = firstName, lastname = lastName)

        Mockito.`when`(repository.registerUser(Mockito.any<User>()?:user, Mockito.anyString())).thenReturn(
            flowOf(Result.loading(), Result.error(error))
        )

        viewModel.registerUser()

        Mockito.verify(observer).onChanged(Result.loading())
        Mockito.verify(observer).onChanged(Result.error(error))
    }

    @FlowPreview
    @Test
    fun `different password typed is failed`()  {

        viewModel.validateData.observeForever(validateObserver)
        viewModel.passwordChannel.offer("111")
        viewModel.passwordConfirmChannel.offer("1111")
        viewModel.emailChannel.offer("vasya@mail.ru")
        viewModel.firstNameChannel.offer("firstName")
        viewModel.lastNameChannel.offer("lastName")

        Mockito.verify(validateObserver).onChanged(ValidateDataError.PASSWORD_NOT_EQUAL)

        viewModel.validateData.removeObserver(validateObserver)
    }

    @FlowPreview
    @Test
    fun `input values all correct`() {

        val password = "password"
        viewModel.validateData.observeForever(validateObserver)
        viewModel.passwordChannel.offer(password)
        viewModel.passwordConfirmChannel.offer(password)
        viewModel.emailChannel.offer("vasya@mail.ru")
        viewModel.firstNameChannel.offer("firstName")
        viewModel.lastNameChannel.offer("lastName")

        Mockito.verify(validateObserver).onChanged(ValidateDataError.OK)

        viewModel.validateData.removeObserver(validateObserver)
    }

    @FlowPreview
    @Test
    fun `incorrect email is failed`() {

        val password = "password"
        viewModel.validateData.observeForever(validateObserver)
        viewModel.passwordChannel.offer(password)
        viewModel.passwordConfirmChannel.offer(password)
        viewModel.emailChannel.offer("vasyamail.ru")
        viewModel.firstNameChannel.offer("firstName")
        viewModel.lastNameChannel.offer("lastName")

        Mockito.verify(validateObserver).onChanged(ValidateDataError.INCORRECT_EMAIL)

        viewModel.validateData.removeObserver(validateObserver)
    }

}

class TestCoroutineProvider: CoroutineContextProvider() {
    override val Main: CoroutineContext = Dispatchers.Unconfined
    override val IO: CoroutineContext = Dispatchers.Unconfined
}
