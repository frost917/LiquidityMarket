package com.frost917.mcserver.market

import org.bukkit.Material
import org.bukkit.entity.Player

data class TradeData(val player: Player, val material: Material, val tradeQuantity: Int) {

}
