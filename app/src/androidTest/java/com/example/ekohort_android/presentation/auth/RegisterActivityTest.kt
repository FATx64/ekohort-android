package com.example.ekohort_android.presentation.auth

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekohort_android.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterActivityTest{

    private val dummyEmail = "muhammadfharidakbar@gmail.com"
    private val dummyPassword = "123321"

    @Before
    fun setup(){
        ActivityScenario.launch(RegisterActivity::class.java)

    }

    @Test
    fun  testRegister(){

        onView(withId(R.id.emailEditText)).perform(typeText(dummyEmail))
        onView(withId(R.id.passwordEditText)).perform(typeText(dummyPassword))

        onView(withId(R.id.button_register)).check(matches(isDisplayed()))
    }

}