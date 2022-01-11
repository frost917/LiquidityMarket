package com.frost917.mcserver.market.storage.database.connection

import org.jetbrains.exposed.sql.Database

interface DatabaseConnection {
    fun createConnection(): Boolean
    fun getConnection(): Database
    fun close(): Boolean

}