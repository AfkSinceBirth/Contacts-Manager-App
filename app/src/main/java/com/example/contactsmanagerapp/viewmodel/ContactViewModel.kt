package com.example.contactsmanagerapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsmanagerapp.repository.ContactRepository
import com.example.contactsmanagerapp.room.Contact
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

//View Model : Store and manage UI-related data in a lifecycle conscious way.
//separating the UI-related logic from UI Controller (Activity/Fragment)

class ContactViewModel(private val repository : ContactRepository)  : ViewModel(){
    val contacts = repository.contacts
    private var isUpdateOrDelete = false
    private lateinit var contactToUpdateOrDelete : Contact


    val inputName = MutableLiveData<String?>()


    val inputEmail =  MutableLiveData<String?>()


    val saveOrUpdateButtonText =  MutableLiveData<String>()


    val clearAllOrDeleteButtonText =  MutableLiveData<String>()

    init{
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun insert(contact : Contact) = viewModelScope.launch{
        repository.insert(contact)
    }

    fun update(contact : Contact) = viewModelScope.launch {
        repository.update(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"
    }

    fun delete(contact : Contact) = viewModelScope.launch {
        repository.delete(contact)

        //resetting the buttons and fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun saveOrUpdate() : Unit{
        if(isUpdateOrDelete){//make an update :
            contactToUpdateOrDelete.contact_name = inputName.value!!
            contactToUpdateOrDelete.contact_email = inputEmail.value!!
            update(contactToUpdateOrDelete)
        }
        else{
            val name = inputName.value
            val email = inputEmail.value
            if(name !=null && email !=null) insert(Contact(0,name,email))

            //resetting the fields
            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete() : Unit{
        if(isUpdateOrDelete){
            delete(contactToUpdateOrDelete)
        }
        else{
            clearAll()
        }

    }

    fun initUpdateOrDelete(contact : Contact){
        inputName.value = contact.contact_name
        inputEmail.value = contact.contact_email
        isUpdateOrDelete = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

//    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        TODO("Not yet implemented")
//    }
//
//    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
//        TODO("Not yet implemented")
//    }


}