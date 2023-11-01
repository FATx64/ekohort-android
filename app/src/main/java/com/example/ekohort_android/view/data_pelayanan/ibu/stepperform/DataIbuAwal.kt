package com.example.ekohort_android.view.data_pelayanan.ibu.stepperform

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataIbuAwalBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class DataIbuAwal : AppCompatActivity() {

    private val calendar = Calendar.getInstance()

    private var dateOfBirth: Long = System.currentTimeMillis()


    private lateinit var binding: ActivityDataIbuAwalBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDataIbuAwalBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.apply {
            binding.edtDoB.setOnClickListener {
                showDatePickerDialog()
            }
        }


    }

    private fun showDatePickerDialog(){
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
            view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatDate = dateFormat.format(calendar.time)
            binding.edtDoB.setText(formatDate)
        }, year, month, day)
        datePickerDialog.show()
    }


}