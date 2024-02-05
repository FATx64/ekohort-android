package com.example.ekohort_android.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface IbuDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(ibu: ibuDb)


    @Update
    fun update(ibu: ibuDb)

    @Delete
    fun delete(ibu: ibuDb)

    @Query("SELECT * from ibudb ORDER BY id ASC")
    fun getAllIbuData(): LiveData<List<ibuDb>>
}