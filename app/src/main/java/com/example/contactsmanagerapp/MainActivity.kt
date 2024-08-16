package com.example.contactsmanagerapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsmanagerapp.viewmodel.ContactViewModel
import com.example.contactsmanagerapp.viewmodel.ViewModelFactory
import com.example.contactsmanagerapp.databinding.ActivityMainBinding
import com.example.contactsmanagerapp.repository.ContactRepository
import com.example.contactsmanagerapp.room.Contact
import com.example.contactsmanagerapp.room.ContactDatabase
import com.example.contactsmanagerapp.view.MyRecyclerViewAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ROOM database
        val dao = ContactDatabase.getInstance(applicationContext).contactDao
        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        //View Model
        contactViewModel = ViewModelProvider(this, factory).get(ContactViewModel::class.java)

        binding.contactViewModel = contactViewModel

        //use this : LiveData and Data Binding Integration
        binding.lifecycleOwner = this

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        DisplayUsersList()
    }

    private fun DisplayUsersList() {
        contactViewModel.contacts.observe(this, Observer{
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it, {selectedItem: Contact -> listItemClicked(selectedItem)})
        })
    }

    private fun listItemClicked(selectedItem : Contact) {
        Toast.makeText(this, "Selected name is ${selectedItem.contact_name}", Toast.LENGTH_SHORT).show()
        contactViewModel.initUpdateOrDelete(selectedItem)
    }
}