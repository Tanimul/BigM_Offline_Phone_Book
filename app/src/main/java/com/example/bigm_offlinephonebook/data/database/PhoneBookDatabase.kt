package com.example.bigm_offlinephonebook.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel

@Database(entities = [PhoneBookModel::class], version = 1, exportSchema = false)
abstract class PhoneBookDatabase : RoomDatabase() {

    abstract fun phoneBookDao(): PhoneBookDao

    companion object {
        @Volatile
        private var instance: PhoneBookDatabase? = null

        fun getDatabase(context: Context): PhoneBookDatabase {
            if (instance == null) {

                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context, PhoneBookDatabase::class.java,
                        "PhoneBook"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance!!

        }
    }

}