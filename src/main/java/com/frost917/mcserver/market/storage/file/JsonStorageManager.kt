package com.frost917.mcserver.market.storage.file

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.TradeData
import com.frost917.mcserver.market.storage.StorageManager
import org.bukkit.Material

class JsonStorageManager: StorageManager, FileStorageManager {
    override fun addItemData(itemData: ItemData): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMarketData(): List<ItemData> {
        TODO("Not yet implemented")
    }

    override fun getMarketData(material: Material): ItemData {
        TODO("Not yet implemented")
    }

    override fun tradeItem(tradeData: TradeData): String {
        TODO("Not yet implemented")
    }

    override fun revalueItem(itemData: ItemData): Boolean {
        TODO("Not yet implemented")
    }


    override fun deleteItemData(material: Material): Boolean {
        TODO("Not yet implemented")
    }

    override fun loadFile() {
        TODO("Not yet implemented")
    }
}