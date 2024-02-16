package com.example.ekohort_android.utils.exts

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

private val calendar = Calendar.getInstance()

fun View.showDatePickerDialog(context: Context) {
    if (this !is EditText) throw IllegalStateException("This view is not an EditText")

    val currentYear = calendar.get(Calendar.YEAR)
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val formatDate = dateFormat.format(calendar.time)
        this.setText(formatDate)
    }, currentYear, currentMonth, currentDay)
    datePickerDialog.show()
}