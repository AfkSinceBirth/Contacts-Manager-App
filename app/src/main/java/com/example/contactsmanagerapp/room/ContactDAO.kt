package com.example.contactsmanagerapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//DAO : Data Access Objects, implements the methods to interact with Database
@Dao
interface ContactDAO {

    @Insert
    suspend fun insertContact(contact : Contact)

    @Update
    suspend fun updateContact(contact : Contact)

    @Delete
    suspend fun deleteContact(contact : Contact)

    @Query("DELETE FROM contacts_table")
    suspend fun deleteAllContact()

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts() : LiveData<List<Contact>>

}