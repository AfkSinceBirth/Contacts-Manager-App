package com.example.contactsmanagerapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey

//Each instance of this class represents a row in the table
@Entity( tableName = "contacts_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var contact_id : Int,
    var contact_name : String,
    var contact_email : String
)
