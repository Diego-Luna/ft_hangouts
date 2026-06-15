package com.dluna_lo_42.ft_hangouts.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.dluna_lo_42.ft_hangouts.model.Contact
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContactRepositoryTest {
    private lateinit var repository: ContactRepository
    private lateinit var dbHelper: DbHelper

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.deleteDatabase(DatabaseContract.DATABASE_NAME)
        dbHelper = DbHelper(context)
        repository = ContactRepository(dbHelper)
    }

    @After
    fun tearDown() {
        dbHelper.close()
    }

    @Test
    fun testInsertAndRetrieveContact() {
        val contact = Contact(
            name = "Diego Luna",
            phoneNumber = "+34612345678",
            email = "diego@example.com",
            address = "Calle Falsa 123",
            company = "42",
            pictureUri = null
        )
        val id = repository.insert(contact)
        assertTrue(id > 0)

        val allContacts = repository.getAll()
        assertEquals(1, allContacts.size)
        
        val retrieved = allContacts[0]
        assertEquals(id, retrieved.id)
        assertEquals(contact.name, retrieved.name)
        assertEquals(contact.phoneNumber, retrieved.phoneNumber)
        assertEquals(contact.email, retrieved.email)
        assertEquals(contact.address, retrieved.address)
        assertEquals(contact.company, retrieved.company)
    }

    @Test
    fun testUpdateContact() {
        val contact = Contact(name = "Original Name", phoneNumber = "123456")
        val id = repository.insert(contact)
        
        val updatedContact = Contact(
            id = id,
            name = "Updated Name",
            phoneNumber = "654321",
            email = "updated@example.com"
        )
        val rows = repository.update(updatedContact)
        assertEquals(1, rows)

        val all = repository.getAll()
        assertEquals(1, all.size)
        assertEquals("Updated Name", all[0].name)
        assertEquals("654321", all[0].phoneNumber)
        assertEquals("updated@example.com", all[0].email)
    }

    @Test
    fun testDeleteContact() {
        val contact = Contact(name = "To Delete", phoneNumber = "999")
        val id = repository.insert(contact)
        assertEquals(1, repository.getAll().size)

        val rows = repository.delete(id)
        assertEquals(1, rows)
        assertEquals(0, repository.getAll().size)
    }
}
