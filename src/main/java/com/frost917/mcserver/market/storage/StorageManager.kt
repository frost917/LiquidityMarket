package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.TradeData
import org.bukkit.Material

interface StorageManager {
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