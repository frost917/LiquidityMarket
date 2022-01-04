package com.frost917.mcserver.market.storage.database

import com.frost917.mcserver.market.storage.StorageType
import com.frost917.mcserver.market.storage.database.connection.*
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.exposed.sql.Database

object DatabaseConnectionFacotory {
    fun getConnection(databaseType: DatabaseType): Database {
        return when (databaseType) {
            DatabaseType.MYSQL -> MysqlConnection().getConnection()
            DatabaseType.MARIADB -> MysqlConnection().getConnection()
            DatabaseType.POSTGRES -> PostgresConnection().getConnection()
            DatabaseType.SQLITE -> SqliteConnection().getConnection()
            DatabaseType.H2 -> H2Connection().getConnection()
        }
    }
}