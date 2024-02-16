package com.example.ekohort_android.presentation.nakes.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDetailIbuBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.nakes.NakesRepository
import com.example.ekohort_android.domain.nakes.model.Nakes
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.presentation.nakes.form.DataNakesAwalActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.toFormattedString
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class DetailNakesActivity : BaseActivity<ActivityDetailIbuBinding>() {
    private lateinit var viewModel: DetailNakesViewModel
    private var currentData: Nakes? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        currentData ?: finish()  // Nuh uh

        viewModel = getViewModel(parameters = { parametersOf(get<NakesRepository>(), currentData!!.id) })

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
    fun setup(data: Nakes) {
        binding.apply {
            tvDetailDataIbu.setText(R.string.detail_data_nakes)
            name.setText(data.name)
            contact.setText(data.phoneNumber)

            tvDataIbu.setText(R.string.data_nakes)
            tvData.setText(
                """
                    NIK: ${data.nik}
                    NIP: ${data.nip}
                    Tanggal Lahir: ${data.birthday.toFormattedString()}
                    Puskesmas: ${data.puskesmas}
                    Jabatan: ${data.role}
                """.trimIndent()
            )

            tvTempatTinggalSekarang.setText(R.string.tempat_tinggal_sekarang)
            tvAddress.setText(
                """
                    Provinsi: ${data.province}
                    Alamat: ${data.address}
                """.trimIndent()
            )

            tvAdditionalTitle.isVisible = false
            tvAdditional.isVisible = false
            btnEdit.setOnClickListener {
                val intent = Intent(this@DetailNakesActivity, DataNakesAwalActivity::class.java).apply {
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