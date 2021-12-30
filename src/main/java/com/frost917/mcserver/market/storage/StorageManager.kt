package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.storage.sqlBase.DatabaseManager
import com.frost917.mcserver.market.storage.sqlBase.ItemData
import com.frost917.mcserver.market.storage.sqlBase.SqliteBase
import org.bukkit.Material
import org.jetbrains.exposed.sql.Database
import java.nio.file.Path

class StorageManager {
    private lateinit var database: Database.Companion
    private lateinit var databaseManager: DatabaseManager

    fun initialize(storageType: StorageType, db_path: Path) {
        if (storageType == StorageType.H2) {
            TODO("not implemented")
        }
        else if (storageType == StorageType.SQLITE) {
            val sqlite = SqliteBase(db_path)
            database = sqlite.getConnection()
        }

        databaseManager = DatabaseManager(database)
    }

    fun initialize(storageType: StorageType, db_host: String, db_name:String, db_id: String, db_passwd: String, db_port: Int) {
        if(storageType == StorageType.MARIADB || storageType == StorageType.MYSQL){
            TODO("not implemented")
        }
        else if (storageType == StorageType.POSTGRES) {
            TODO("not implemented")
        }

        databaseManager = DatabaseManager(database)
    }

    // 모든 아이템 데이터 불러오기
    fun getItemData(): List<ItemData> {
        return databaseManager.getItemData()
    }

    fun getItemData(material: Material): ItemData {
        return databaseManager.getItemData(material)
    }

    fun syncItemData(material: Material, totalQuantity: Int) {
        return databaseManager.syncItemData(material, totalQuantity)
    }

    fun addNewItem(itemData: ItemData) {
        return databaseManager.addNewItem(itemData)
    }

    fun deleteItem(material: Material) {
        return databaseManager.deleteItem(material)
    }
}