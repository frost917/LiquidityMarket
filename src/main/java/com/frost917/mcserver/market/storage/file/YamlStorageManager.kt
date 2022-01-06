package com.frost917.mcserver.market.storage.file

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.storage.StorageManager
import org.bukkit.Material

class YamlStorageManager: FileStorageManager, StorageManager {
    override fun addItemData(itemData: ItemData): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMarketData(): List<ItemData> {
        TODO("Not yet implemented")
    }

    override fun getMarketData(material: Material): ItemData {
        TODO("Not yet implemented")
    }

    override fun syncItemData(itemData: ItemData): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteItemData(material: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun loadFile() {
        TODO("Not yet implemented")
    }
}