package com.example.ekohort_android.presentation.ibu.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataIbuAwalBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.toDate
import com.example.ekohort_android.utils.exts.toFormattedString
import com.example.ekohort_android.utils.exts.toInt
import com.example.ekohort_android.utils.exts.toLong
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DataIbuAwalActivity : BaseActivity<ActivityDataIbuAwalBinding>() {

    private val calendar = Calendar.getInstance()

    private val viewModel: DataIbuViewModel by viewModel()
    private var dateOfBirth: Long = System.currentTimeMillis()
    private var currentData: Ibu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        super.onCreate(savedInstanceState)
        binding = ActivityDataIbuAwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (currentData != null) {
                val current = currentData!!
                titleTambahData.setText(R.string.ubah_data_ibu)
                edtNamaIbu.setText(current.name)
                edtnik.setText(current.nik.toString())
                edtkk.setText(current.kk.toString())
                edtDoB.setText(current.birthday.toFormattedString())
                provinceSpinner.setSelection(resources.getStringArray(R.array.provinsi_array).indexOf(current.province))
                edtAlamat.setText(current.address)
                edtTb.setText(current.height.toString())
                edtBb.setText(current.weight.toString())
                edtDiagnosa.setText(current.diagnose)
                edtWa.setText(current.phoneNumber)
                edtTanggalKunjungan.setText(current.visitDate.toFormattedString())
                edtTanggalKunjunganBerikutnya.setText(current.nextVisit.toFormattedString())
                edtWa.setText(current.phoneNumber)
            }
            edtDoB.setOnClickListener { it.showDatePickerDialog() }
            edtTanggalKunjungan.setOnClickListener { it.showDatePickerDialog() }
            edtTanggalKunjunganBerikutnya.setOnClickListener { it.showDatePickerDialog() }
            btnSubmit.setOnClickListener {
                MaterialAlertDialogBuilder(this@DataIbuAwalActivity)
                    .setTitle("Simpan Data Ibu?")
                    .setMessage("Pastikan semua data sudah benar sebelum disimpan")
                    .setPositiveButton(R.string.save) { _, _ ->
                        val ibu = Ibu(
                            name = binding.edtNamaIbu.text.toString(),
                            nik = binding.edtnik.text.toLong(),
                            kk = binding.edtkk.text.toLong(),
                            birthday = binding.edtDoB.text.toDate(),
                            province = binding.provinceSpinner.selectedItem.toString(),
                            address = binding.edtAlamat.text.toString(),
                            height = binding.edtTb.text.toInt(),
                            weight = binding.edtBb.text.toInt(),
                            diagnose = binding.edtDiagnosa.text.toString(),
                            visitDate = binding.edtTanggalKunjungan.text.toDate(),
                            nextVisit = binding.edtTanggalKunjunganBerikutnya.text.toDate(),
                            phoneNumber = binding.edtWa.text.toString()
                        )
                        if (currentData == null) viewModel.insert(ibu) else viewModel.update(currentData!!.id, ibu)
                        // Optimistic approach, cuz I can't be bothered to do something else
                        finish()
                    }
                    .setNegativeButton("Kembali", null)
                    .show()
            }

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.state.collect {
                            when (it) {
                                is Result.Error -> {
                                    it.exception?.let { exception ->
                                        toast("${it.message} ${exception::class.qualifiedName}")
                                        true
                                    } ?: toast(it.message)
                                }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
        spinnerProvince()
    }

    private fun View.showDatePickerDialog(){
        if (this !is EditText) throw IllegalStateException("This view is not an EditText")

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this@DataIbuAwalActivity, { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formatDate = dateFormat.format(calendar.time)
            this.setText(formatDate)
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