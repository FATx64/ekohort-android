package com.example.ekohort_android.presentation.onboarding

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.ekohort_android.R
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.ekohort_android.presentation.auth.LoginActivity
import org.junit.After
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class OnboardingScreen1Test{

    @get:Rule
    val activityTestRule = ActivityTestRule(OnboardingScreen1::class.java)

    @Before
    fun setup(){
        Intents.init()
    }

    @After
    fun tearDown(){
        Intents.release()
    }

    @Test
    fun testButton(){
       Thread.sleep(2000)

        onView(withId(R.id.btn_continue)).perform(click())

        intended(hasComponent(OnboardingScreen2::class.java.name))

        Thread.sleep(2000)

        pressBack()

        Thread.sleep(2000)





    }

}