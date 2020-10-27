package com.mrozon.feature_auth.presentation

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mrozon.feature_auth.R
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    }

    @Test
    fun all_input_value_is_corrected() {

        launchFragmentInContainer<LoginFragment>()

        val password = "Password1!"
        Espresso.onView(ViewMatchers.withId(R.id.etUserName)).perform(ViewActions.typeText("vasya@mail.ru"))
        Espresso.onView(ViewMatchers.withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .check(ViewAssertions.matches(ViewMatchers.isEnabled()))
    }

    @Test
    fun input_incorrect_email() {

        launchFragmentInContainer<LoginFragment>()

        val password = "Password1!"
        Espresso.onView(ViewMatchers.withId(R.id.etUserName)).perform(ViewActions.typeText("vasyamail.ru"))
        Espresso.onView(ViewMatchers.withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
    }

    @Test
    fun input_empty_password() {

        launchFragmentInContainer<LoginFragment>()

        val password = ""
        Espresso.onView(ViewMatchers.withId(R.id.etUserName)).perform(ViewActions.typeText("vasya@mail.ru"))
        Espresso.onView(ViewMatchers.withId(R.id.etUserPassword)).perform(ViewActions.typeText(password))

        Espresso.onView(ViewMatchers.withId(R.id.btnLogin))
            .check(ViewAssertions.matches(not(ViewMatchers.isEnabled())))
    }
}