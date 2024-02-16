package com.example.ekohort_android.presentation.anak.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.ekohort_android.R
import com.example.ekohort_android.databinding.ActivityDetailIbuBinding
import com.example.ekohort_android.domain.Result
import com.example.ekohort_android.domain.anak.AnakRepository
import com.example.ekohort_android.domain.anak.model.Anak
import com.example.ekohort_android.domain.anak.model.Anak.Companion.gender
import com.example.ekohort_android.presentation.anak.form.DataAnakAwalActivity
import com.example.ekohort_android.presentation.base.BaseActivity
import com.example.ekohort_android.utils.exts.getParcelableExtraCompat
import com.example.ekohort_android.utils.exts.toFormattedString
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class DetailAnakActivity : BaseActivity<ActivityDetailIbuBinding>() {
    private lateinit var viewModel: DetailAnakViewModel
    private var currentData: Anak? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentData = intent.getParcelableExtraCompat("ekohort_android.current")
        currentData ?: finish()  // Nuh uh

        viewModel = getViewModel(parameters = { parametersOf(get<AnakRepository>(), currentData!!.id) })

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
    fun setup(data: Anak) {
        binding.apply {
            tvDetailDataIbu.setText(R.string.detail_data_anak)
            name.setText(data.name)
            contact.setText(data.visitDate.toFormattedString())

            tvDataIbu.setText(R.string.data_ortu)
            tvData.setText(
                """
                    Nama Ibu: ${data.momName}
                    Nama Ayah: ${data.dadName}
                    Nomor HP / WA : ${data.parentContact}
                """.trimIndent()
            )

            tvTempatTinggalSekarang.setText(R.string.data_anak)
            tvAddress.setText(
                """
                    NIK Anak: ${data.nik}
                    Nama Anak: ${data.name}
                    Jenis Kelamin: ${data.gender.gender()}
                    Tanggal Lahir: ${data.birthday.toFormattedString()}
                    Umur: ${getAge(data.birthday, data.visitDate)}
                    Alamat: ${data.address}
                """.trimIndent()
            )

            tvAdditionalTitle.setText(R.string.data_riwayat_kesehatan)
            tvAdditional.setText(
                """
                    Berat Badan : ${data.weight}
                    Keterangan : ${data.description}
                    Tanggal Kunjungan : ${data.visitDate.toFormattedString()}
                    Tanggal Kunjungan Berikutnya : ${data.nextVisit.toFormattedString()}
                """.trimIndent()
            )
            btnEdit.setOnClickListener {
                val intent = Intent(this@DetailAnakActivity, DataAnakAwalActivity::class.java).apply {
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

    private fun getAge(birthday: Date, range: Date): Int {
        val cal = Calendar.getInstance()
        cal.time = birthday
        val target = Calendar.getInstance()
        target.time = range

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val targetYear = target.get(Calendar.YEAR)
        val targetMonth = target.get(Calendar.MONTH)
        val targetDay = target.get(Calendar.DAY_OF_MONTH)

        val age = targetYear - year
        if ((targetMonth < month) || (targetMonth == month) && (targetDay < day)) {
            age - 1
        }

        return age
    }
}