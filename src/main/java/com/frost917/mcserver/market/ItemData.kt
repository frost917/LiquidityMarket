package com.frost917.mcserver.market

import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.market.itemManager.SaleData
import org.bukkit.Material

data class ItemData(val material: Material, val marketValue: Long, val totalQuantity: Int) {
    fun toSaleData() : SaleData {
        return ItemValueManager.calcItemValue(this)
    }
}
