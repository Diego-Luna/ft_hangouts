package com.dluna_lo_42.ft_hangouts.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseContract.DATABASE_NAME,
    null,
    DatabaseContract.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        val createContactsTable = """
            CREATE TABLE ${DatabaseContract.ContactEntry.TABLE_NAME} (
                ${DatabaseContract.ContactEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DatabaseContract.ContactEntry.COLUMN_NAME} TEXT NOT NULL,
                ${DatabaseContract.ContactEntry.COLUMN_PHONE} TEXT NOT NULL UNIQUE,
                ${DatabaseContract.ContactEntry.COLUMN_EMAIL} TEXT,
                ${DatabaseContract.ContactEntry.COLUMN_ADDRESS} TEXT,
                ${DatabaseContract.ContactEntry.COLUMN_COMPANY} TEXT,
                ${DatabaseContract.ContactEntry.COLUMN_PICTURE_URI} TEXT
            )
        """.trimIndent()

        val createMessagesTable = """
            CREATE TABLE ${DatabaseContract.MessageEntry.TABLE_NAME} (
                ${DatabaseContract.MessageEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DatabaseContract.MessageEntry.COLUMN_CONTACT_ID} INTEGER,
                ${DatabaseContract.MessageEntry.COLUMN_CONTENT} TEXT NOT NULL,
                ${DatabaseContract.MessageEntry.COLUMN_TIMESTAMP} INTEGER NOT NULL,
                ${DatabaseContract.MessageEntry.COLUMN_IS_SENT} INTEGER NOT NULL,
                FOREIGN KEY(${DatabaseContract.MessageEntry.COLUMN_CONTACT_ID}) 
                    REFERENCES ${DatabaseContract.ContactEntry.TABLE_NAME}(${DatabaseContract.ContactEntry.COLUMN_ID}) 
                    ON DELETE CASCADE
            )
        """.trimIndent()

        db.execSQL(createContactsTable)
        db.execSQL(createMessagesTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.MessageEntry.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.ContactEntry.TABLE_NAME}")
        onCreate(db)
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }
}
