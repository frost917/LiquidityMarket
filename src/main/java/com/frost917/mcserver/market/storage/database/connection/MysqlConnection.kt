package com.frost917.mcserver.market.storage.database.connection

import org.jetbrains.exposed.sql.Database

class MysqlConnection: DatabaseConnection {
    override fun createConnection(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getConnection(): Database {
        TODO("Not yet implemented")
    }

    override fun close(): Boolean {
        TODO("Not yet implemented")
    }
}