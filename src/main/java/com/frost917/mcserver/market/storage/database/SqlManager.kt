package com.frost917.mcserver.market.storage.database

import org.jetbrains.exposed.sql.*
import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.TradeData
import com.frost917.mcserver.market.storage.StorageManager
import org.apache.commons.lang.RandomStringUtils
import org.bukkit.Material
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class SqlManager(private val database: Database) : StorageManager {
    private val plugin = Main.MainPlugin.getPlugin()

    init {
        plugin.logger.info("LiquidityMarket: Market Initialize start!")
        initDatabase()
        plugin.logger.info("LiquidityMarket: Market Initialize complete!")
    }

    // 아이템 창고
    object ItemDataQuery: Table("lqmarket_item_storage") {
        val material: Column<String> = varchar("material", 30).primaryKey()
        val marketValue: Column<Long> = long("market_value")
        val totalQuantity: Column<Int> = integer("total_quantity")
    }

    // 거래 내역
    object TransactionTable: Table("lqmarket_trade_history") {
        val transaction_id: Column<String> = varchar("transaction_id", 8).primaryKey()
        val playerID: Column<UUID> = uuid("player_id")
        val material = reference("material", ItemDataQuery.material)
        val tradeQuantity: Column<Int> = integer("trade_quantity")
    }

    private fun initDatabase() {
        plugin.logger.info("LiquidityMarket: Initialize market tables")
        transaction(database) {
            SchemaUtils.create(ItemDataQuery)
            plugin.logger.info("LiquidityMarket: Create item storage table")
            SchemaUtils.create(TransactionTable)
            plugin.logger.info("LiquidityMarket: Create trade history table")
        }
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
                val material = Material.getMaterial(it[ItemDataQuery.material]) ?: throw Exception(
                    "Material data is broken!\n" + "Material: ${it[ItemDataQuery.material]}"
                )
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
            ItemDataQuery.select { ItemDataQuery.material eq material.toString() }.forEach {
                val marketValue = it[ItemDataQuery.marketValue]
                val totalQuantity = it[ItemDataQuery.totalQuantity]

                itemData = ItemData(material, marketValue, totalQuantity)
            }
        }

        return itemData
    }

    // marketValue 변동에 대비해 totalQuantity 및 marketValue를 변경함
    // 거래 내역을 저장하기 위해 거래 ID를 생성 후 반환
    override fun tradeItem(tradeData: TradeData): String {
        // 거래 ID 생성(8자리)
        val transactionID = RandomStringUtils.random(8, true, true)
        var originQuantity: Int ?= null


        transaction(database) {
            // 원본 아이템 개수 불러오기
            ItemDataQuery.select { ItemDataQuery.material eq ItemDataQuery.material.toString() }.forEach {
                originQuantity = it[ItemDataQuery.totalQuantity]
            }
            // 아이템 거래 반영
            ItemDataQuery.update({ ItemDataQuery.material eq tradeData.material.toString() }) {
                it[totalQuantity] = originQuantity?.plus(tradeData.tradeQuantity)!!
            }

            // 거래 내역 작성
            TransactionTable.insert {
                it[transaction_id] = transactionID
                it[playerID] = tradeData.player.playerProfile.id ?: throw Exception(
                    "LiquidityMarket: Trade history making failed!" +
                            "player id is null" +
                            "player name: ${tradeData.player.displayName()}"
                )
                it[material] = tradeData.material.toString()
                it[tradeQuantity] = tradeData.tradeQuantity
            }
       }

        return transactionID
    }

    override fun revalueItem(itemData: ItemData): Boolean {
        transaction(database) {
            ItemDataQuery.update({ ItemDataQuery.material eq itemData.material.toString() }) {
                it[totalQuantity] = itemData.totalQuantity
                it[marketValue] = itemData.marketValue
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