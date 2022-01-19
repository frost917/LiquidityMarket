package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.TradeData
import org.bukkit.Material

interface StorageManager {
    // TODO!! material 말고 item meta도 serialize를 하든 string화 하든 해서 저장할 것
    // Create
    fun addItemData(itemData: ItemData): Boolean
    // Read
    fun getMarketData(): List<ItemData>
    fun getMarketData(material: Material): ItemData
    // Update
    fun tradeItem(tradeData: TradeData): String
    fun revalueItem(itemData: ItemData): Boolean
    // Delete
    fun deleteItemData(material: Material): Boolean
}