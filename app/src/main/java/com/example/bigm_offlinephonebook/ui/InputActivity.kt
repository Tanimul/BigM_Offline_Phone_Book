package com.example.bigm_offlinephonebook.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.bigm_offlinephonebook.R
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel
import com.example.bigm_offlinephonebook.databinding.ActivityInputBinding
import com.example.bigm_offlinephonebook.utils.Constants
import com.example.bigm_offlinephonebook.utils.Constants.REQUEST_CODE_ADD_PHONE_BOOK
import com.example.bigm_offlinephonebook.utils.Constants.REQUEST_CODE_UPDATE_PHONE_BOOK
import com.example.bigm_offlinephonebook.utils.extentions.toast
import com.example.bigm_offlinephonebook.viewmodels.PhoneBookViewModel
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

class InputActivity : AppBaseActivity() {
    companion object {
        private val TAG = "InputActivity"
    }

    private lateinit var binding: ActivityInputBinding
    private lateinit var phoneBookViewModel: PhoneBookViewModel
    private lateinit var existingphoneBookModel: PhoneBookModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set toolbar
        setToolbar(binding.toolbarLayout.toolbar)


        title = if (intent.extras != null) {
            "Update Phone Book"
        } else {
            "Add Phone Book"
        }

        if (intent.extras != null) {
            existingphoneBookModel =
                intent.extras?.getSerializable("phoneBookModel") as PhoneBookModel
            Log.d(TAG, "onCreate: $existingphoneBookModel")
            binding.etName.setText(existingphoneBookModel.name)
            binding.etEmail.setText(existingphoneBookModel.email)
            binding.etPhone.setText(existingphoneBookModel.mobileNumber)
            binding.btnAdd.text = "Update"

        }

        //phoneBook viewModel initialize
        phoneBookViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PhoneBookViewModel::class.java]

        binding.btnAdd.setOnClickListener {
            savePhoneBook()
        }


    }

    private fun savePhoneBook() {
        if (binding.etName.text.toString().isEmpty() || binding.etEmail.text.toString()
                .isEmpty() || binding.etPhone.text.toString().isEmpty()||!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            Log.d(TAG, "Fill the all filed ")
            toast("Fill the all filed")
        } else {

            val phoneBookModel = PhoneBookModel(
                name = binding.etName.text.toString(),
                email = binding.etEmail.text.toString(),
                mobileNumber = binding.etPhone.text.toString(),
                updatedAt = System.currentTimeMillis(),
            )

            val resultIntent = Intent()

            if (intent.extras != null) {
                resultIntent.putExtra(
                    "phoneBookModel", phoneBookModel as Serializable
                )
                resultIntent.putExtra("existingPhoneBookID", existingphoneBookModel.id)
                setResult(
                    REQUEST_CODE_UPDATE_PHONE_BOOK, resultIntent
                )
                Log.d(TAG, "editPhoneBook: ")

            } else {

                setResult(
                    REQUEST_CODE_ADD_PHONE_BOOK,
                    resultIntent.putExtra("phoneBookModel", phoneBookModel as Serializable)
                )
                Log.d(TAG, "addPhoneBook: ")
            }
            finish()
        }


    }
}