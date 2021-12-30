package com.frost917.mcserver.market.storage.sqlBase

import org.jetbrains.exposed.sql.*
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path

class SqliteBase(db_path: Path) {
    private var database = Database

    init {
        try {
            database.connect("jdbc:sqlite:$db_path", driver="org.sqlite.JDBC")
        }
        catch (e: FileNotFoundException) {
            var file = File(db_path.toString())
            file.createNewFile()
            database.connect("jdbc:sqlite:$db_path", driver="org.sqlite.JDBC")
        }
    }

    fun getConnection(): Database.Companion {
        return database
    }
}