package com.shadowvault.core.database

import androidx.room.withTransaction

class DatabaseTransactionHelper(
    private val db: MovieFlixDatabase
) {
    suspend fun runInTransaction(block: suspend () -> Unit) {
        db.withTransaction {
            block()
        }
    }
}