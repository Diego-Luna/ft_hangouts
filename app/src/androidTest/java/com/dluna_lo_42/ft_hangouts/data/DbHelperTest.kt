package com.dluna_lo_42.ft_hangouts.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DbHelperTest {
    private lateinit var dbHelper: DbHelper

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DatabaseContract.DATABASE_NAME)
        dbHelper = DbHelper(context)
    }

    @After
    fun tearDown() {
        dbHelper.close()
    }

    @Test
    fun testDatabaseCreation() {
        val db = dbHelper.readableDatabase
        assertTrue(db.isOpen)
        
        // Verify contacts table exists
        val contactCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='${DatabaseContract.ContactEntry.TABLE_NAME}'", null)
        assertTrue(contactCursor.moveToFirst())
        contactCursor.close()

        // Verify messages table exists
        val messageCursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='${DatabaseContract.MessageEntry.TABLE_NAME}'", null)
        assertTrue(messageCursor.moveToFirst())
        messageCursor.close()
    }
}
