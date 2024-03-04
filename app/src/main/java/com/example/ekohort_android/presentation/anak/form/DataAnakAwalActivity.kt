package com.example.ekohort_android.presentation.anak.form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataAnakAwalBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.anak.model.Anak
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.showDatePickerDialog
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

class DataAnakAwalActivity : BaseActivity<ActivityDataAnakAwalBinding>() {

    private val viewModel: DataAnakViewModel by viewModel()
    private var dateOfBirth: Long = System.currentTimeMillis()
    private var currentData: Anak? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        super.onCreate(savedInstanceState)
        binding = ActivityDataAnakAwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (currentData != null) {
                val current = currentData!!
                titleTambahData.setText(R.string.ubah_data_anak)
                edtNamaAyah.setText(current.dadName)
                edtNamaIbu.setText(current.momName)
                edtNama.setText(current.name)
                edtnik.setText(current.nik.toString())
                edtDoB.setText(current.birthday.toFormattedString())
                genderSpinner.setSelection(current.gender)
                edtAlamat.setText(current.address)
                edtBb.setText(current.weight.toString())
                edtDiagnosa.setText(current.description)
                edtTanggalKunjungan.setText(current.visitDate.toFormattedString())
                edtTanggalKunjunganBerikutnya.setText(current.nextVisit.toFormattedString())
                edtWa.setText(current.parentContact)
            }
            edtDoB.setOnClickListener { it.showDatePickerDialog(this@DataAnakAwalActivity) }
            edtTanggalKunjungan.setOnClickListener { it.showDatePickerDialog(this@DataAnakAwalActivity) }
            edtTanggalKunjunganBerikutnya.setOnClickListener { it.showDatePickerDialog(this@DataAnakAwalActivity) }
            btnSubmit.setOnClickListener {
                MaterialAlertDialogBuilder(this@DataAnakAwalActivity)
                    .setTitle("Simpan Data Anak?")
                    .setMessage("Pastikan semua data sudah benar sebelum disimpan")
                    .setPositiveButton(R.string.save) { _, _ ->
                        val data = Anak(
                            dadName = binding.edtNamaAyah.text.toString(),
                            momName = binding.edtNamaIbu.text.toString(),
                            name = binding.edtNama.text.toString(),
                            nik = binding.edtnik.text.toLong(),
                            birthday = binding.edtDoB.text.toDate(),
                            gender = genderSpinner.selectedItemPosition,
                            address = binding.edtAlamat.text.toString(),
                            weight = binding.edtBb.text.toInt(),
                            description = binding.edtDiagnosa.text.toString(),
                            visitDate = binding.edtTanggalKunjungan.text.toDate(),
                            nextVisit = binding.edtTanggalKunjunganBerikutnya.text.toDate(),
                            parentContact = binding.edtWa.text.toString()
                        )
                        if (currentData == null) viewModel.insert(data) else viewModel.update(currentData!!.id, data)
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

    private fun spinnerProvince(){
        val spinner = binding.genderSpinner
        val adapter = ArrayAdapter(
            this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.gender),
        )
        spinner.adapter = adapter
    }
}