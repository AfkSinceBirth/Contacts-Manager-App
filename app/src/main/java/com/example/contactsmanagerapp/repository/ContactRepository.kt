package com.example.contactsmanagerapp.repository

import com.example.contactsmanagerapp.room.Contact
import com.example.contactsmanagerapp.room.ContactDAO

class ContactRepository(private val contactDAO: ContactDAO) {
    val contacts = contactDAO.getAllContacts()

    suspend fun insert(contact : Contact){
        return contactDAO.insertContact(contact)
    }

    suspend fun update(contact : Contact){
        return contactDAO.updateContact(contact)
    }

    suspend fun delete(contact : Contact){
        return contactDAO.deleteContact(contact)
    }

    suspend fun deleteAll(){
        return contactDAO.deleteAllContact()

    }
}