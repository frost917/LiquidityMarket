package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.storage.database.SqlManager
import com.frost917.mcserver.market.storage.database.connection.*
import com.frost917.mcserver.market.storage.file.JsonStorageManager
import com.frost917.mcserver.market.storage.file.YamlStorageManager

object StorageManagerFactory {
    fun getStorage(storageType: StorageType): StorageManager {
        return when(storageType) {
            StorageType.MYSQL -> SqlManager(MysqlConnection().getConnection())
            StorageType.MARIADB -> SqlManager(MysqlConnection().getConnection())
            StorageType.POSTGRES -> SqlManager(PostgresConnection().getConnection())
            StorageType.SQLITE -> SqlManager(SqliteConnection().getConnection())
            StorageType.H2 -> SqlManager(H2Connection().getConnection())

            StorageType.JSON -> JsonStorageManager()
            StorageType.YAML -> YamlStorageManager()
        }
    }
}