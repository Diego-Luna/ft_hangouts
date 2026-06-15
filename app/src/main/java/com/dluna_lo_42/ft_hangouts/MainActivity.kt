package com.dluna_lo_42.ft_hangouts

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dluna_lo_42.ft_hangouts.data.ContactRepository
import com.dluna_lo_42.ft_hangouts.data.DbHelper
import com.dluna_lo_42.ft_hangouts.model.Contact

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DbHelper
    private lateinit var repository: ContactRepository

    private lateinit var tvDbStatus: TextView
    private lateinit var tvContactsDump: TextView
    private lateinit var btnInsertDummy: Button
    private lateinit var btnClearDb: Button

    private var dummyCounter = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DbHelper(this)
        repository = ContactRepository(dbHelper)

        tvDbStatus = findViewById(R.id.tvDbStatus)
        tvContactsDump = findViewById(R.id.tvContactsDump)
        btnInsertDummy = findViewById(R.id.btnInsertDummy)
        btnClearDb = findViewById(R.id.btnClearDb)

        btnInsertDummy.setOnClickListener {
            val dummyContact = Contact(
                name = "John Doe $dummyCounter",
                phoneNumber = "+346001122${30 + dummyCounter}",
                email = "john$dummyCounter@example.com",
                address = "Main Street $dummyCounter",
                company = "42 School"
            )
            repository.insert(dummyContact)
            dummyCounter++
            refreshUi()
        }

        btnClearDb.setOnClickListener {
            val contacts = repository.getAll()
            for (contact in contacts) {
                repository.delete(contact.id)
            }
            dummyCounter = 1
            refreshUi()
        }

        refreshUi()
    }

    private fun refreshUi() {
        val contacts = repository.getAll()
        tvDbStatus.text = "Database Status: Connected (${contacts.size} contacts)"
        
        if (contacts.isEmpty()) {
            tvContactsDump.text = "No contacts in database."
        } else {
            val builder = StringBuilder()
            for (c in contacts) {
                builder.append("ID: ${c.id}\n")
                    .append("Name: ${c.name}\n")
                    .append("Phone: ${c.phoneNumber}\n")
                    .append("Email: ${c.email ?: "N/A"}\n")
                    .append("Company: ${c.company ?: "N/A"}\n")
                    .append("--------------------\n")
            }
            tvContactsDump.text = builder.toString()
        }
    }

    override fun onDestroy() {
        dbHelper.close()
        super.onDestroy()
    }
}