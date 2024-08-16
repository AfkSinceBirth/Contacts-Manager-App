package com.example.contactsmanagerapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase  : RoomDatabase() {

    abstract val contactDao : ContactDAO

    //Singleton Design Pattern

    companion object{

        @Volatile
        private var INSTANCE: ContactDatabase? =null

        fun getInstance(context : Context) : ContactDatabase {

            return INSTANCE ?: synchronized(this){
                if(INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(
                                        context.applicationContext,
                                        ContactDatabase::class.java,
                                        "contacts_database"
                                        ).build()
                }
                return INSTANCE!!
            }
        }

    }
}