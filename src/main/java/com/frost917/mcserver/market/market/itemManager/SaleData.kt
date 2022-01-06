package com.frost917.mcserver.market.market.itemManager

import net.kyori.adventure.text.Component
import org.bukkit.Material

data class SaleData(val material: Material, val nowValue: Int, val stackValue: Int) {
    fun toLore(): List<Component> {
        val lore = mutableListOf<Component>()
        lore.add(Component.text(("개당 가격: $nowValue")))

        val stackQuantity = stackValue / nowValue

        lore.add(Component.text(("$stackQuantity 가격: $stackValue")))
        return lore
    }
}

