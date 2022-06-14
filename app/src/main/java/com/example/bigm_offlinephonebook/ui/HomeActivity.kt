package com.example.bigm_offlinephonebook.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bigm_offlinephonebook.R
import com.example.bigm_offlinephonebook.adapter.PhoneBookAdapter
import com.example.bigm_offlinephonebook.data.listner.OnPhoneBookClickListener
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel
import com.example.bigm_offlinephonebook.databinding.ActivityHomeBinding
import com.example.bigm_offlinephonebook.utils.Constants.REQUEST_CODE_ADD_PHONE_BOOK
import com.example.bigm_offlinephonebook.utils.Constants.REQUEST_CODE_UPDATE_PHONE_BOOK
import com.example.bigm_offlinephonebook.utils.extentions.toast
import com.example.bigm_offlinephonebook.viewmodels.PhoneBookViewModel
import java.io.Serializable
import java.util.ArrayList

class HomeActivity : AppBaseActivity(), OnPhoneBookClickListener {
    companion object {
        private const val TAG: String = "HomeActivity"
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var phoneBookViewModel: PhoneBookViewModel
    private lateinit var phoneBookList: ArrayList<PhoneBookModel>
    private lateinit var phoneBookAdapter: PhoneBookAdapter
    private var optionMenu: Menu? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Set toolbar
        setToolbarWithoutBackButton(binding.toolbarLayout.toolbar)
        title = null

        //phone Book viewModel initialize
        phoneBookViewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PhoneBookViewModel::class.java]


        //arrayList initialize
        phoneBookList = ArrayList<PhoneBookModel>()

        phoneBookAdapter = PhoneBookAdapter(phoneBookList, phoneBookList, this)


        //recyclerView
        binding.rvPhoneBookList.layoutManager = LinearLayoutManager(this)
        binding.rvPhoneBookList.adapter = phoneBookAdapter

        //showBookList
        showPhoneBookList()

        //go to the Input Activity
        binding.fabInput.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            homeActResult.launch(intent)
        }




        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d(TAG, "onQueryTextChange: $s")
                phoneBookAdapter.filter.filter(s)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        Log.d(TAG, "onOptionsItemSelected: " + item.itemId)
        when (item.itemId) {
            R.id.menu_delete_all -> {
                deleteAll()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    @SuppressLint("RestrictedApi")
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (!isEmptyPhoneBook()) {
            val menuInflater = menuInflater
            menuInflater.inflate(R.menu.top_menu, menu)
            optionMenu = menu
            if (menu is MenuBuilder) {
                menu.setOptionalIconsVisible(true)
            }


            Log.d(TAG, "onCreateOptionsMenu: " + binding.rvPhoneBookList.layoutManager)

        }

        return true
    }

    private val homeActResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            isEmptyPhoneBook()
            Log.d(TAG, "Result Code: " + it.resultCode)
            if (it.resultCode != RESULT_CANCELED) {
                val phoneBookModel: PhoneBookModel =
                    it.data?.getSerializableExtra("phoneBookModel") as PhoneBookModel
                if (it.resultCode == REQUEST_CODE_ADD_PHONE_BOOK) {
                    Log.d(TAG, "ok Add: " + it.data?.getSerializableExtra("phoneBookModel"))
                    if (it.data != null) {
                        phoneBookViewModel.addSinglePhoneBook(phoneBookModel)
                        toast("Phone Book Saved")
                    }

                } else if (it.resultCode == REQUEST_CODE_UPDATE_PHONE_BOOK) {
                    Log.d(
                        TAG,
                        "ok Edit: " + it.data?.getSerializableExtra("phoneBookModel") + " -> and id is: " + it.data?.getIntExtra(
                            "existingPhoneBookID", -1
                        )
                    )
                    if (it.data != null) {
                        val id = it.data?.getIntExtra("existingPhoneBookID", -1)
                        if (id != null) {
                            phoneBookModel.id = id
                        }
                        phoneBookViewModel.updateExistingSinglePhoneBook(phoneBookModel)
                        toast("Phone Book Saved")
                    }

                }
            }

        }


    private fun showPhoneBookList() {
        phoneBookViewModel.showAllPhoneBookList.observe(
            this
        ) {
            phoneBookList.clear()
            phoneBookList.addAll(it)
            phoneBookAdapter.notifyDataSetChanged()
        }

    }


    private fun deleteAll() {
        AlertDialog.Builder(this@HomeActivity)
            .setTitle("Delete All")
            .setMessage("Are you sure you want to delete all Phone Books?")
            .setPositiveButton(
                "OK"
            ) { _, _ -> phoneBookViewModel.deleteAllPhoneBook() }
            .setNegativeButton(
                "CANCEL"
            ) { dialog, _ -> dialog.dismiss() }
            .show()

    }


    private fun isEmptyPhoneBook(): Boolean {
        invalidateOptionsMenu()
        var noPhoneBook = true
        phoneBookViewModel.showAllPhoneBookList.observe(
            this
        ) {
            binding.emptyLayout.root.isVisible = it.isEmpty()
            binding.etSearch.isVisible = it.isNotEmpty()
            noPhoneBook = it.isEmpty()
        }
        return noPhoneBook
    }

    override fun onItemEditClick(phoneBookModel: PhoneBookModel) {
        Log.d(TAG, "onItemClick: $phoneBookModel and id is: " + phoneBookModel.id)
        homeActResult.launch(
            Intent(this, InputActivity::class.java).putExtra(
                "phoneBookModel",
                phoneBookModel as Serializable
            )
        )
    }

    override fun onItemDeleteClick(phoneBookModel: PhoneBookModel) {
        phoneBookViewModel.deleteSinglePhoneBook(phoneBookModel)
        phoneBookAdapter.notifyDataSetChanged()
    }
}

