package com.example.ekohort_android.presentation.ibu.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.databinding.ActivityDetailIbuBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.ibu.IbuRepository
import com.example.ekohort_android.domain.ibu.model.Ibu
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.ibu.form.DataIbuAwalActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.toFormattedString
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class DetailIbuActivity : BaseActivity<ActivityDetailIbuBinding>() {
    private lateinit var viewModel: DetailIbuViewModel
    private var currentData: Ibu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        currentData ?: finish()  // Nuh uh

        viewModel = getViewModel(parameters = { parametersOf(get<IbuRepository>(), currentData!!.id) })

        super.onCreate(savedInstanceState)

        binding = ActivityDetailIbuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            setup(currentData!!)

            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.state.collect {
                            when (it) {
                                is Result.Success -> it.data?.let { data -> setup(data) }
                                else -> {}
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setup(data: Ibu) {
        binding.apply {
            name.setText(data.name)
            contact.setText(data.phoneNumber)
            tvData.setText(
                """
                    NIK : ${data.nik}
                    KK : ${data.kk}
                    Tanggal Lahir : ${data.birthday.toFormattedString()}
                """.trimIndent()
            )
            tvAddress.setText(
                """
                    Provinsi : ${data.province}
                    Alamat: ${data.address}
                """.trimIndent()
            )
            tvAdditional.setText(
                """
                    Berat Badan : ${data.weight}
                    Tinggi Badang : ${data.height}
                    Tanggal Kunjungan : ${data.visitDate.toFormattedString()}
                    Tanggal Kunjungan Berikutnya : ${data.nextVisit.toFormattedString()}
                """.trimIndent()
            )
            btnEdit.setOnClickListener {
                val intent = Intent(this@DetailIbuActivity, DataIbuAwalActivity::class.java).apply {
                    putExtra("ekohort_android.current", data)
                }
                startActivity(intent)
            }
            btnDelete.setOnClickListener {
                viewModel.delete(data)
                // Optimistic!!!!
                finish()
            }
        }
    }
}