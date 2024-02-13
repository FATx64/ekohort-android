package com.example.ekohort_android.presentation.ibu.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataIbuAwalBinding
import com.example.ekohort_android.presentation.base.BaseActivity
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DataIbuAwalActivity : BaseActivity<ActivityDataIbuAwalBinding>() {

    private val calendar = Calendar.getInstance()

    private var dateOfBirth: Long = System.currentTimeMillis()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataIbuAwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            binding.edtDoB.setOnClickListener {
                showDatePickerDialog()
            }
        }
        spinnerProvince()
    }

    private fun showDatePickerDialog(){
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatDate = dateFormat.format(calendar.time)
            binding.edtDoB.setText(formatDate)
        }, currentYear, currentMonth, currentDay)
        datePickerDialog.show()
    }

    private fun spinnerProvince(){
        val provinceList = resources.getStringArray(R.array.provinsi_array)

        val spinner = binding.provinceSpinner
        val adapter = ArrayAdapter(this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, provinceList)
        spinner.adapter = adapter
    }
}