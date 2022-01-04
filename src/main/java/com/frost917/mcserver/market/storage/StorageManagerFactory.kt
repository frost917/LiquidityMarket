package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.storage.database.DatabaseConnectionFacotory
import com.frost917.mcserver.market.storage.database.SqlManager
import com.frost917.mcserver.market.storage.file.JsonStorageManager
import com.frost917.mcserver.market.storage.file.YamlStorageManager

object StorageManagerFactory {
    fun getStorage(storageType: StorageType): StorageManager {
        return when(storageType) {
            StorageType.REMOTE -> SqlManager()
            StorageType.JSON -> JsonStorageManager()
            StorageType.YAML -> YamlStorageManager()
        }
    }
}