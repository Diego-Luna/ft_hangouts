package com.dluna_lo_42.ft_hangouts.data

import android.content.ContentValues
import android.database.Cursor
import com.dluna_lo_42.ft_hangouts.model.Contact

class ContactRepository(private val dbHelper: DbHelper) {

    fun insert(contact: Contact): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.ContactEntry.COLUMN_NAME, contact.name)
            put(DatabaseContract.ContactEntry.COLUMN_PHONE, contact.phoneNumber)
            put(DatabaseContract.ContactEntry.COLUMN_EMAIL, contact.email)
            put(DatabaseContract.ContactEntry.COLUMN_ADDRESS, contact.address)
            put(DatabaseContract.ContactEntry.COLUMN_COMPANY, contact.company)
            put(DatabaseContract.ContactEntry.COLUMN_PICTURE_URI, contact.pictureUri)
        }
        return db.insert(DatabaseContract.ContactEntry.TABLE_NAME, null, values)
    }

    fun getAll(): List<Contact> {
        val db = dbHelper.readableDatabase
        val contacts = mutableListOf<Contact>()
        
        val projection = arrayOf(
            DatabaseContract.ContactEntry.COLUMN_ID,
            DatabaseContract.ContactEntry.COLUMN_NAME,
            DatabaseContract.ContactEntry.COLUMN_PHONE,
            DatabaseContract.ContactEntry.COLUMN_EMAIL,
            DatabaseContract.ContactEntry.COLUMN_ADDRESS,
            DatabaseContract.ContactEntry.COLUMN_COMPANY,
            DatabaseContract.ContactEntry.COLUMN_PICTURE_URI
        )

        val cursor: Cursor = db.query(
            DatabaseContract.ContactEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            "${DatabaseContract.ContactEntry.COLUMN_NAME} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_NAME))
                val phone = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PHONE))
                val email = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_EMAIL))
                val address = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ADDRESS))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_COMPANY))
                val picture = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PICTURE_URI))

                contacts.add(Contact(id, name, phone, email, address, company, picture))
            }
            close()
        }
        return contacts
    }

    fun getById(id: Long): Contact? {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            DatabaseContract.ContactEntry.COLUMN_ID,
            DatabaseContract.ContactEntry.COLUMN_NAME,
            DatabaseContract.ContactEntry.COLUMN_PHONE,
            DatabaseContract.ContactEntry.COLUMN_EMAIL,
            DatabaseContract.ContactEntry.COLUMN_ADDRESS,
            DatabaseContract.ContactEntry.COLUMN_COMPANY,
            DatabaseContract.ContactEntry.COLUMN_PICTURE_URI
        )

        val cursor = db.query(
            DatabaseContract.ContactEntry.TABLE_NAME,
            projection,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var contact: Contact? = null
        with(cursor) {
            if (moveToFirst()) {
                val name = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_NAME))
                val phone = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PHONE))
                val email = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_EMAIL))
                val address = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_ADDRESS))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_COMPANY))
                val picture = getString(getColumnIndexOrThrow(DatabaseContract.ContactEntry.COLUMN_PICTURE_URI))
                contact = Contact(id, name, phone, email, address, company, picture)
            }
            close()
        }
        return contact
    }

    fun update(contact: Contact): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseContract.ContactEntry.COLUMN_NAME, contact.name)
            put(DatabaseContract.ContactEntry.COLUMN_PHONE, contact.phoneNumber)
            put(DatabaseContract.ContactEntry.COLUMN_EMAIL, contact.email)
            put(DatabaseContract.ContactEntry.COLUMN_ADDRESS, contact.address)
            put(DatabaseContract.ContactEntry.COLUMN_COMPANY, contact.company)
            put(DatabaseContract.ContactEntry.COLUMN_PICTURE_URI, contact.pictureUri)
        }

        return db.update(
            DatabaseContract.ContactEntry.TABLE_NAME,
            values,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(contact.id.toString())
        )
    }

    fun delete(id: Long): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            DatabaseContract.ContactEntry.TABLE_NAME,
            "${DatabaseContract.ContactEntry.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }
}
