package com.mrozon.feature_auth.presentation

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mrozon.feature_auth.R
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationFragmentTest {

    lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    }

    @Test
    fun all_input_value_is_corrected() {

        launchFragmentInContainer<RegistrationFragment>()

        val password = "Password1!"
        onView(withId(R.id.etUserName)).perform(ViewActions.typeText("vasya@mail.ru"))
        onView(withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etUserPasswordAgain)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etFirstName)).perform(ViewActions.typeText("first_name"))
        onView(withId(R.id.etLastName)).perform(ViewActions.typeText("last_name"))

        onView(withId(R.id.btnRegistration)).check(matches(isEnabled()))
    }

    @Test
    fun input_incorrect_email() {

        launchFragmentInContainer<RegistrationFragment>()

        val password = "Password1!"
        onView(withId(R.id.etUserName)).perform(ViewActions.typeText("vasyamail.ru"))
        onView(withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etUserPasswordAgain)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etFirstName)).perform(ViewActions.typeText("first_name"))
        onView(withId(R.id.etLastName)).perform(ViewActions.typeText("last_name"))

        onView(withId(R.id.etUserName)).check(matches(hasErrorText(context.getString(R.string.uncorrect_email))))
    }

    @Test
    fun input_different_passwords() {

        launchFragmentInContainer<RegistrationFragment>()

        onView(withId(R.id.etUserName)).perform(ViewActions.typeText("vasya@mail.ru"))
        onView(withId(R.id.etUserPassword)).perform(ViewActions.typeText("password"))
        onView(withId(R.id.etUserPasswordAgain)).perform(ViewActions.typeText("1"))
        onView(withId(R.id.etFirstName)).perform(ViewActions.typeText("first_name"))
        onView(withId(R.id.etLastName)).perform(ViewActions.typeText("last_name"))

        onView(withId(R.id.etUserPassword)).check(matches(hasErrorText(context.getString(R.string.equals_psw))))
    }

    @Test
    fun dont_type_all_needed_params() {

        launchFragmentInContainer<RegistrationFragment>()

        val password = "Password1!"
        onView(withId(R.id.etUserName)).perform(ViewActions.typeText("vasya@mail.ru"))
        onView(withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etUserPasswordAgain)).perform(ViewActions.typeText(password))
        onView(withId(R.id.etFirstName)).perform(ViewActions.typeText("first_name"))

        onView(withId(R.id.btnRegistration)).check(matches(not(isEnabled())))
    }

}