package com.frost917.mcserver.market.storage.sqlBase

import org.bukkit.Material
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager(private val database: Database.Companion) {
    object ItemDataTable: Table() {
        val material: Column<String> = varchar("material", 30)
        val marketValue: Column<Long> = long("market_value")
        val totalQuantity: Column<Int> = integer("total_quantity")
    }

    fun getItemData(): List<ItemData> {
        var itemList = mutableListOf<ItemData>()
        transaction {
            val query: Query = ItemDataTable.selectAll()

            query.forEach {
                val material = Material.getMaterial(it[ItemDataTable.material])!!
                val marketValue = it[ItemDataTable.marketValue]
                val totalQuantity = it[ItemDataTable.totalQuantity]

                val itemData = ItemData(material, marketValue, totalQuantity)
                itemList.add(itemData)
            }
        }
        return itemList
    }

    fun getItemData(originMaterial: Material): ItemData {
        var material = Material.AIR
        var marketValue = 0L
        var totalQuantity = 0

        transaction {
            val query: Query = ItemDataTable.select {
                ItemDataTable.material eq originMaterial.name
            }

            // 해당 아이템이 등록 안된 경우 사전에 정의된 기본 값으로 넘김
            if (!query.empty()) {
                query.forEach {
                    material = Material.getMaterial(it[ItemDataTable.material])!!
                    marketValue = it[ItemDataTable.marketValue]
                    totalQuantity = it[ItemDataTable.totalQuantity]
                }
            }
        }

        return ItemData(material, marketValue, totalQuantity)
    }

    fun syncItemData(material: Material, totalQuantity: Int) {
        transaction {
            ItemDataTable.update({ ItemDataTable.material eq material.name }) {
                it[ItemDataTable.totalQuantity] = totalQuantity
            }
        }
    }

    fun addNewItem(itemData: ItemData) {
        transaction {
            ItemDataTable.insert {
                it[material] = itemData.material.name
                it[marketValue] = itemData.marketValue
                it[totalQuantity] = itemData.totalQuantity
            }
        }
    }

    fun deleteItem(material: Material) {
        transaction {
            ItemDataTable.deleteWhere {
                ItemDataTable.material eq material.name
            }
        }
    }
}