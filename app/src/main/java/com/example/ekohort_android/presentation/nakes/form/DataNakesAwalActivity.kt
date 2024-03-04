package com.example.ekohort_android.presentation.nakes.form

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDataNakesAwalBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.model.Nakes
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.showDatePickerDialog
import com.example.ekohort_android.utils.exts.toDate
import com.example.ekohort_android.utils.exts.toFormattedString
import com.example.ekohort_android.utils.exts.toLong
import com.example.ekohort_android.utils.exts.toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DataNakesAwalActivity : BaseActivity<ActivityDataNakesAwalBinding>() {

    private val viewModel: DataNakesViewModel by viewModel()
    private var dateOfBirth: Long = System.currentTimeMillis()
    private var currentData: Nakes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        super.onCreate(savedInstanceState)
        binding = ActivityDataNakesAwalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            if (currentData != null) {
                val current = currentData!!
                titleTambahData.setText(R.string.ubah_data_nakes)
                edtNama.setText(current.name)
                edtnik.setText(current.nik.toString())
                edtnip.setText(current.nip.toString())
                edtDoB.setText(current.birthday.toFormattedString())
                edtPuskesmas.setText(current.puskesmas)
                edtRole.setText(current.role)
                provinceSpinner.setSelection(resources.getStringArray(R.array.provinsi_array).indexOf(current.province))
                edtAlamat.setText(current.address)
                edtWa.setText(current.phoneNumber)
            }
            edtDoB.setOnClickListener { it.showDatePickerDialog(this@DataNakesAwalActivity) }
            btnSubmit.setOnClickListener {
                MaterialAlertDialogBuilder(this@DataNakesAwalActivity)
                    .setTitle("Simpan Data Nakes?")
                    .setMessage("Pastikan semua data sudah benar sebelum disimpan")
                    .setPositiveButton(R.string.save) { _, _ ->
                        val data = Nakes(
                            name = binding.edtNama.text.toString(),
                            nik = binding.edtnik.text.toLong(),
                            nip = binding.edtnip.text.toLong(),
                            birthday = binding.edtDoB.text.toDate(),
                            puskesmas = binding.edtPuskesmas.text.toString(),
                            role = binding.edtRole.text.toString(),
                            province = binding.provinceSpinner.selectedItem.toString(),
                            address = binding.edtAlamat.text.toString(),
                            phoneNumber = binding.edtWa.text.toString(),
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
        val provinceList = resources.getStringArray(R.array.provinsi_array)

        val spinner = binding.provinceSpinner
        val adapter = ArrayAdapter(this,
            androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, provinceList)
        spinner.adapter = adapter
    }
}