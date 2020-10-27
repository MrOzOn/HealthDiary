package com.mrozon.feature_auth.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mrozon.core_api.entity.User
import com.mrozon.feature_auth.data.CoroutineTestRule
import com.mrozon.feature_auth.data.UserAuthRepository
import com.mrozon.utils.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class LoginFragmentViewModelTest {

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
    lateinit var validateObserver: Observer<Boolean>

    lateinit var viewModel: LoginFragmentViewModel

    @Before
    fun setUp() {
        viewModel = LoginFragmentViewModel(repository, TestCoroutineProvider())
            .apply {
                loggedUser.observeForever(observer)
            }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `login user success`()  = coroutinesTestRule.runBlockingTest {
        val email = "user@user.ru"
        val password = "password"
        viewModel.userNameChannel.offer(email)
        viewModel.userPasswordChannel.offer(password)
        val user = User(email = email, firstname = "firstName", lastname = "lastName")
        Mockito.`when`(repository.loginUser(Mockito.anyString(), Mockito.anyString())).thenReturn(
            flowOf(Result.loading(), Result.success(user))
        )

        viewModel.loginUser()

        Mockito.verify(observer).onChanged(Result.loading())
        Mockito.verify(observer).onChanged(Result.success(user))
    }

    @Test
    fun `login user failed`()  = coroutinesTestRule.runBlockingTest {
        val email = "user@user.ru"
        val password = "password"
        val error = "Bla-bla!"
        viewModel.userNameChannel.offer(email)
        viewModel.userPasswordChannel.offer(password)
        Mockito.`when`(repository.loginUser(Mockito.anyString(), Mockito.anyString())).thenReturn(
            flowOf(Result.loading(), Result.error(error))
        )

        viewModel.loginUser()

        Mockito.verify(observer).onChanged(Result.loading())
        Mockito.verify(observer).onChanged(Result.error(error))
    }

    @FlowPreview
    @Test
    fun `email typed is invalid`()  {

        viewModel.enableLogin.observeForever(validateObserver)
        viewModel.userNameChannel.offer("sdfgsds")
        viewModel.userPasswordChannel.offer("1111")

        Mockito.verify(validateObserver).onChanged(false)

        viewModel.enableLogin.removeObserver(validateObserver)
    }

    @FlowPreview
    @Test
    fun `password is empty`()  {

        viewModel.enableLogin.observeForever(validateObserver)
        viewModel.userNameChannel.offer("sdfgs@ds.ru")
        viewModel.userPasswordChannel.offer("")

        Mockito.verify(validateObserver).onChanged(false)

        viewModel.enableLogin.removeObserver(validateObserver)
    }

    @FlowPreview
    @Test
    fun `all inputs are correct`()  {

        viewModel.enableLogin.observeForever(validateObserver)
        viewModel.userNameChannel.offer("sdfgs@ds.ru")
        viewModel.userPasswordChannel.offer("password")

        Mockito.verify(validateObserver).onChanged(true)

        viewModel.enableLogin.removeObserver(validateObserver)
    }

}