package com.example.ekohort_android.presentation.ibu.form

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataIbuAwalBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.model.Ibu
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

class DataIbuAwalActivity : BaseActivity<ActivityDataIbuAwalBinding>() {

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
                edtNama.setText(current.name)
                edtnik.setText(current.nik.toString())
                edtkk.setText(current.kk.toString())
                edtDoB.setText(current.birthday.toFormattedString())
                provinceSpinner.setSelection(resources.getStringArray(R.array.provinsi_array).indexOf(current.province))
                edtAlamat.setText(current.address)
                edtTb.setText(current.height.toString())
                edtBb.setText(current.weight.toString())
                edtDiagnosa.setText(current.diagnose)
                edtTanggalKunjungan.setText(current.visitDate.toFormattedString())
                edtTanggalKunjunganBerikutnya.setText(current.nextVisit.toFormattedString())
                edtWa.setText(current.phoneNumber)
            }
            edtDoB.setOnClickListener { it.showDatePickerDialog(this@DataIbuAwalActivity) }
            edtTanggalKunjungan.setOnClickListener { it.showDatePickerDialog(this@DataIbuAwalActivity) }
            edtTanggalKunjunganBerikutnya.setOnClickListener { it.showDatePickerDialog(this@DataIbuAwalActivity) }
            btnSubmit.setOnClickListener {
                MaterialAlertDialogBuilder(this@DataIbuAwalActivity)
                    .setTitle("Simpan Data Ibu?")
                    .setMessage("Pastikan semua data sudah benar sebelum disimpan")
                    .setPositiveButton(R.string.save) { _, _ ->
                        val ibu = Ibu(
                            name = binding.edtNama.text.toString(),
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
        val provinceList = resources.getStringArray(R.array.provinsi_array)

        val spinner = binding.provinceSpinner
        val adapter = ArrayAdapter(this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, provinceList)
        spinner.adapter = adapter
    }
}