package com.frost917.mcserver.market.storage.database.connection

import com.frost917.mcserver.market.Main
import org.jetbrains.exposed.sql.Database
import kotlin.io.path.Path
import kotlin.io.path.createFile

class SqliteConnection() : DatabaseConnection {
    private var database: Database ?= null

    init {
        createConnection()
    }

    override fun createConnection(): Boolean {
        val plugin = Main.MainPlugin.getPlugin()
        val dbPath = plugin.config.getString("db_path")

        val path = Path(dbPath!!)
        if (!path.toFile().exists()) {
            path.createFile()
        }

        database = Database.connect("jdbc:sqlite:$dbPath", "org.sqlite.JDBC")
        return true
    }

    override fun getConnection(): Database {
        // 커넥션 정보가 없으면 생성
        if (database == null) {
            createConnection()
        }

        return database!!
    }

    override fun close(): Boolean {
        TODO("Not yet implemented")
    }
}