package com.example.ekohort_android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ibuDb::class], version = 1)
abstract class IbuRoomDatabase  : RoomDatabase(){

    abstract fun ibuDao(): IbuDao

    companion object{
        @Volatile
        private var INSTANCE : IbuRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): IbuRoomDatabase{
            if (INSTANCE == null){
                synchronized(IbuRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        IbuRoomDatabase::class.java, "ibu_database")
                        .build()
                }
            }
            return INSTANCE as IbuRoomDatabase
        }
    }

}