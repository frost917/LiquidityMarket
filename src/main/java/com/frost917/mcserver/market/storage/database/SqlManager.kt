package com.frost917.mcserver.market.storage.database

import com.frost917.mcserver.market.Main
import org.jetbrains.exposed.sql.*
// import org.jetbrains.exposed.dao.*


import com.frost917.mcserver.market.storage.ItemData
import com.frost917.mcserver.market.storage.StorageManager
import com.frost917.mcserver.market.storage.database.connection.DatabaseType
import org.bukkit.Material
import org.jetbrains.exposed.sql.transactions.transaction

class SqlManager : StorageManager {
    private val database: Database

    init {
        val plugin = Main.MainPlugin.getPlugin()
        val config = plugin.config
        val databaseType = DatabaseType.valueOf(config.getString("db_type")!!)
        database = DatabaseConnectionFacotory.getConnection(databaseType)
    }

    object ItemDataQuery: Table() {
        val material: Column<String> = varchar("material", 30)
        val marketValue: Column<Long> = long("market_value")
        val totalQuantity: Column<Int> = integer("total_quantity")
    }

    override fun addItemData(itemData: ItemData): Boolean {
        transaction(database) {
            ItemDataQuery.insert {
                it[material] = itemData.material.toString()
                it[marketValue] = itemData.marketValue
                it[totalQuantity] = itemData.totalQuantity
            }
        }
        return true
    }

    override fun getMarketData(): List<ItemData> {
        var itemList = mutableListOf<ItemData>()

        transaction(database) {
            ItemDataQuery.selectAll().forEach {
                val material = Material.getMaterial(it[ItemDataQuery.material])!!
                val marketValue = it[ItemDataQuery.marketValue]
                val totalQuantity = it[ItemDataQuery.totalQuantity]

                itemList.add(ItemData(material, marketValue, totalQuantity))
            }
        }

        // 아이템 데이터가 없는 경우용
        if (itemList.isEmpty()){
            itemList.add(ItemData(Material.AIR, 0, 0))
        }

        return itemList
    }

    override fun getMarketData(material: Material): ItemData {
        var itemData = ItemData(Material.AIR, 0, 0)

        transaction(database) {
            ItemDataQuery.select() { ItemDataQuery.material eq material.toString() }.forEach {
                val material = Material.getMaterial(it[ItemDataQuery.material])!!
                val marketValue = it[ItemDataQuery.marketValue]
                val totalQuantity = it[ItemDataQuery.totalQuantity]

                itemData = ItemData(material, marketValue, totalQuantity)
            }
        }

        return itemData
    }

    override fun syncItemData(itemData: ItemData): Boolean {
        transaction(database) {
            ItemDataQuery.update({ ItemDataQuery.material eq itemData.material.toString() }) {
                it[totalQuantity] = itemData.totalQuantity
            }
        }
        return true
    }

    override fun deleteItemData(material: Material): Boolean {
        transaction(database) {
            ItemDataQuery.deleteWhere {  ItemDataQuery.material eq material.toString() }
        }

        return true
    }
}