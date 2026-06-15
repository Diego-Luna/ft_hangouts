package com.dluna_lo_42.ft_hangouts.data

import android.provider.BaseColumns

object DatabaseContract {
    const val DATABASE_NAME = "ft_hangouts.db"
    const val DATABASE_VERSION = 1

    object ContactEntry : BaseColumns {
        const val TABLE_NAME = "contacts"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone_number"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ADDRESS = "address"
        const val COLUMN_COMPANY = "company"
        const val COLUMN_PICTURE_URI = "picture_uri"
    }

    object MessageEntry : BaseColumns {
        const val TABLE_NAME = "messages"
        const val COLUMN_ID = "_id"
        const val COLUMN_CONTACT_ID = "contact_id"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_IS_SENT = "is_sent" // 1 for sent, 0 for received
    }
}
