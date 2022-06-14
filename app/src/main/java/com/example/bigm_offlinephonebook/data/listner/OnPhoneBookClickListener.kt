package com.example.bigm_offlinephonebook.data.listner

import com.example.bigm_offlinephonebook.data.model.PhoneBookModel


interface OnPhoneBookClickListener {
    fun onItemEditClick(phoneBookModel: PhoneBookModel)
    fun onItemDeleteClick(phoneBookModel: PhoneBookModel)
}