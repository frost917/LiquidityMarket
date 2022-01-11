package com.frost917.mcserver.market

import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.market.itemManager.SaleData
import org.bukkit.Material

data class ItemData(val material: Material, val marketValue: Long, val totalQuantity: Int) {
    fun toSaleData() : SaleData {
        return ItemValueManager.calcItemValue(this)
    }

    fun getTradeQuantity(): Int {
        val storage = Main.MainPlugin.getStorage()
        val itemData = storage.getMarketData(material)

        // 플레이어가 아이템 구매시 -
        // 아이템 판매시 +
        return totalQuantity - itemData.totalQuantity
    }
}
