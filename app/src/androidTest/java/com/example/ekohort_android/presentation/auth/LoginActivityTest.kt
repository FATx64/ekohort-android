package com.example.ekohort_android.presentation.auth


import androidx.core.content.MimeTypeFilter.matches
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekohort_android.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest{

    private val dummyEmail = "muhammadfharidakbar@gmail.com"
    private val password = "123321"

    @Before
    fun setup(){
        ActivityScenario.launch(LoginActivity::class.java)
    }
    @Test
    fun testLogin(){

       onView(withId(R.id.emailEditText)).perform(typeText(dummyEmail))
       onView(withId(R.id.passwordEditText)).perform(typeText(password))

       onView(withId(R.id.button_login)).check(matches(isDisplayed()))

    }

}