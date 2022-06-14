package com.example.bigm_offlinephonebook.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.bigm_offlinephonebook.data.database.PhoneBookDatabase
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel
import com.example.bigm_offlinephonebook.data.repository.PhoneBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhoneBookViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "PhoneBookViewModel"
    val showAllPhoneBookList: LiveData<List<PhoneBookModel>>
    private val phoneBookRepository: PhoneBookRepository

    init {
        val phoneBookDao = PhoneBookDatabase.getDatabase(application).phoneBookDao()
        phoneBookRepository = PhoneBookRepository(phoneBookDao)
        showAllPhoneBookList = phoneBookRepository.showAllPhoneBookList
    }

    fun addSinglePhoneBook(phoneBookModel: PhoneBookModel) = viewModelScope.launch(Dispatchers.IO) {
        phoneBookRepository.addSinglePhoneBook(phoneBookModel)
    }

    fun updateExistingSinglePhoneBook(phoneBookModel: PhoneBookModel) = viewModelScope.launch(Dispatchers.IO) {
        phoneBookRepository.updateExistingPhoneBook(phoneBookModel)
    }

    fun deleteSinglePhoneBook(phoneBookModel: PhoneBookModel) = viewModelScope.launch(Dispatchers.IO) {
        phoneBookRepository.deleteSinglePhoneBook(phoneBookModel)
    }

    fun deleteAllPhoneBook() = viewModelScope.launch(Dispatchers.IO) {
        phoneBookRepository.deleteAllPhoneBook()
    }

}