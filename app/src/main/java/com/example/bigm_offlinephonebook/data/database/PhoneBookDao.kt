package com.example.bigm_offlinephonebook.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel

@Dao
interface PhoneBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSinglePhoneBook(phoneBookModel: PhoneBookModel) //Add single Phone Book


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateExistingPhoneBook(phoneBookModel: PhoneBookModel) //update single Phone Book


    @Delete
    suspend fun deleteSinglePhoneBook(phoneBookModel: PhoneBookModel) //delete single Phone Book


    @Query("DELETE FROM PhoneBook")
    suspend fun deleteAllPhoneBook() //delete all Phone Book


    @Query("SELECT * FROM PhoneBook ORDER BY name ASC")
    fun showAllPhoneBookList(): LiveData<List<PhoneBookModel>> //showing all Phone Book


}