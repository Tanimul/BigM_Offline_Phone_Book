package com.example.bigm_offlinephonebook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.bigm_offlinephonebook.data.listner.OnPhoneBookClickListener
import com.example.bigm_offlinephonebook.data.model.PhoneBookModel
import com.example.bigm_offlinephonebook.databinding.LayoutPhoneBookBinding
import java.util.*


class PhoneBookAdapter(
    var lists: List<PhoneBookModel>,
    phoneBookList: ArrayList<PhoneBookModel>,
    private val onPhoneBookClickListener: OnPhoneBookClickListener
) :
    RecyclerView.Adapter<PhoneBookAdapter.PhoneBookViewHolder>(), Filterable {
    private val TAG = "PhoneBookAdapter"


    inner class PhoneBookViewHolder(val binding: LayoutPhoneBookBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneBookViewHolder {
        return PhoneBookViewHolder(
            LayoutPhoneBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PhoneBookViewHolder, position: Int) {
        with(holder) {
            with(lists[position]) {
                binding.tvName.text = name
                binding.tvPhone.text = mobileNumber
                binding.tvEmail.text = email
            }
        }

        holder.binding.ibEdit.setOnClickListener {
            onPhoneBookClickListener.onItemEditClick(lists[position])
        }
        holder.binding.ibDelete.setOnClickListener {
            onPhoneBookClickListener.onItemDeleteClick(lists[position])
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }


    override fun getFilter(): Filter {
        return quearyfilter
    }

    private var quearyfilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val results = FilterResults()
            if (constraint.isEmpty()) {
                results.count = phoneBookList.size
                results.values = phoneBookList
            } else {
                val searchStr = constraint.toString().uppercase()
                val resultsData: MutableList<PhoneBookModel> = ArrayList()
                for (noteInfo in phoneBookList) {
                    if (noteInfo.name.uppercase()
                            .contains(searchStr)
                    ) resultsData.add(noteInfo)
                }
                results.count = resultsData.size
                results.values = resultsData
            }
            Log.d(TAG, "result count: " + results.count)
            Log.d(TAG, "result values: " + results.values)
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            lists = results.values as ArrayList<PhoneBookModel>
            Log.d(TAG, "publishResults: " + lists.size)
            notifyDataSetChanged()
        }
    }

}