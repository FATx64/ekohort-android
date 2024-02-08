package com.example.ekohort_android.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "ibuDb")
@Parcelize

data class ibuDb (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "nik")
    var nik: Int? = null,

    @ColumnInfo(name = "address")
    var address: String? = null,

    @ColumnInfo(name = "dayOfBirth")
    var dayOfBirth : String? = null,

    @ColumnInfo(name = "age")
    var age : String? = null,

    @ColumnInfo(name = "tb")
    var tb : String? = null,

    @ColumnInfo(name = "bb")
    var bb : String? = null,

    @ColumnInfo(name = "bpjs")
    var bpjs : String? = null,

    @ColumnInfo(name = "tglKunjungan")
    var tglKunjungan : String? = null,

    @ColumnInfo(name = "tglKunjunganBerikutnya")
    var tglKunjunganBerikutnya : String? = null,

    @ColumnInfo(name = "contact")
    var contact : String? = null

) : Parcelable

