package com.frost917.mcserver.market.storage

import com.frost917.mcserver.market.ItemData
import org.bukkit.Material

interface StorageManager {
    // Create
    abstract fun addItemData(itemData: ItemData): Boolean
    // Read
    abstract fun getMarketData(): List<ItemData>
    abstract fun getMarketData(material: Material): ItemData
    // Update
    abstract fun syncItemData(itemData: ItemData): Boolean
    // Delete
    abstract fun deleteItemData(material: Material): Boolean
}