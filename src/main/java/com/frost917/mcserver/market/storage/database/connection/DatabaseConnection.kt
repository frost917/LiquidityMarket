package com.frost917.mcserver.market.storage.database.connection

import org.bukkit.configuration.file.FileConfiguration
import org.jetbrains.exposed.sql.Database
import org.bukkit.plugin.java.JavaPlugin

interface DatabaseConnection {
    abstract fun createConnection(): Boolean
    abstract fun getConnection(): Database
    abstract fun close(): Boolean

}