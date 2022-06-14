package com.example.bigm_offlinephonebook.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.bigm_offlinephonebook.data.database.PhoneBookDao
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel

class PhoneBookRepository(private val phoneBookDao: PhoneBookDao) {
    private val TAG= "PhoneBookRepository"
    var showAllPhoneBookList: LiveData<List<PhoneBookModel>> = phoneBookDao.showAllPhoneBookList()

    suspend fun addSinglePhoneBook(phoneBookModel: PhoneBookModel) {
        phoneBookDao.addSinglePhoneBook(phoneBookModel)
        Log.d(TAG, "addSinglePhoneBook: ")
    }

    suspend fun deleteSinglePhoneBook(phoneBookModel: PhoneBookModel) {
        phoneBookDao.deleteSinglePhoneBook(phoneBookModel)
        Log.d(TAG, "deleteSinglePhoneBook: ")
    }

    suspend fun updateExistingPhoneBook(phoneBookModel: PhoneBookModel) {
        phoneBookDao.updateExistingPhoneBook(phoneBookModel)
        Log.d(TAG, "updateExistingPhoneBook: ")
    }

    suspend fun deleteAllPhoneBook() {
        phoneBookDao.deleteAllPhoneBook()
        Log.d(TAG, "deleteAllPhoneBook: ")
    }
}