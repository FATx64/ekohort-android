package com.example.ekohort_android.presentation.ibu.form

import android.view.View
import android.widget.DatePicker
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ekohort_android.R
import com.google.common.base.CharMatcher.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.CoreMatchers.`is`
import org.junit.runner.RunWith
import java.util.regex.Matcher

@RunWith(AndroidJUnit4::class)
class DataIbuAwalActivityTest{

    private val dummyName = "JohnDoe"
    private val dummyNik = "1647384838483"
    private val dummyKk = "1674569495934"
    private val dummyProvince = "Sumatera Selatan"
    private val dummyAddress = "Lembak"
    private val dummyTb = "160"
    private val dummyBb = "60"
    private val dummyDiagnose = "Test Diagnosa"
    private val dummyContact = "0823234355634"

    @Before
    fun setup(){
        ActivityScenario.launch(DataIbuAwalActivity::class.java)

    }

    @Test
    fun testFillForm(){

        Thread.sleep(2000)

        onView(withId(R.id.edtNama)).perform(typeText(dummyName))
        onView(withId(R.id.edtnik)).perform(typeText(dummyNik))
        onView(withId(R.id.edtkk)).perform(typeText(dummyKk))

        onView(withId(R.id.edtDoB)).perform(click())
        //onView(isAssignableFrom(DatePicker::class.java)).inRoot(RootMatchers.isDialog()).perform(


            /*
        onView(withId(R.id.provinceSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("Nama Provinsi"))).perform(click())

        onView(withId(R.id.edtAlamat)).perform(typeText(dummyAddress))
        onView(withId(R.id.edt_tb)).perform(typeText(dummyTb))
        onView(withId(R.id.edt_bb)).perform(typeText(dummyBb))
        onView(withId(R.id.edt_diagnosa)).perform(typeText(dummyDiagnose))

        onView(withId(R.id.edtTanggalKunjungan)).perform(click())

        onView(withId(R.id.tanggal_kunjungan_berikutnya)).perform(click())

        onView(withId(R.id.edt_wa)).perform(typeText(dummyContact))

        Thread.sleep(2000)

        intended(hasComponent(DataIbuAwalActivity::class.java.name), times(0))

             */


    }


}
/*

private fun setDate(year: Int, monthOfYear: Int, dayOfMonth: Int): ViewAction {
    return object : ViewAction {
        override fun getDescription(): String {
            return "Set date on DatePicker"
        }

        override fun getConstraints(): Matcher<View> {
            return allOf(isAssignableFrom(DatePicker::class.java), isDisplayed())
        }

        override fun perform(uiController: UiController?, view: View?) {
            val datePicker = view as DatePicker
            datePicker.init(year, monthOfYear, dayOfMonth, null)
        }
    }
}

 */



