package com.example.bigm_offlinephonebook.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "PhoneBook")
data class PhoneBookModel(
    val name: String,
    val mobileNumber: String,
    val email: String,
    val addedAt: Long=System.currentTimeMillis(),
    val updatedAt: Long,
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}