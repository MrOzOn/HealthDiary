package com.mrozon.feature_person.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.providers.CoroutineContextProvider
import com.mrozon.feature_person.data.CoroutineTestRule
import com.mrozon.feature_person.data.PersonRepository
import com.mrozon.utils.Event
import com.mrozon.utils.network.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
import java.util.*
import kotlin.coroutines.CoroutineContext

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class ListPersonFragmentViewModelTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()


    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<Event<Result<List<Person>>>>

    @Mock
    lateinit var repository: PersonRepository

    lateinit var viewModel: ListPersonFragmentViewModel

    @Before
    fun setUp() {
        viewModel = ListPersonFragmentViewModel(repository, TestCoroutineProvider())
            .apply {
                persons.observeForever(observer)
            }
    }

    @Test
    fun `refresh persons success`()  = coroutinesTestRule.runBlockingTest {
        val person = Person(id=100, name="", gender = Gender.MALE, born = Date())
        Mockito.`when`(repository.refreshPersons()).thenReturn(
            flowOf(Result.loading(), Result.success(listOf(person)))
        )

        viewModel.refreshPersons()

        Mockito.verify(observer).onChanged(Event(Result.loading()))
        Mockito.verify(observer).onChanged(Event(Result.success(listOf(person))))
    }

}

class TestCoroutineProvider: CoroutineContextProvider() {
    override val Main: CoroutineContext = Dispatchers.Unconfined
    override val IO: CoroutineContext = Dispatchers.Unconfined
}