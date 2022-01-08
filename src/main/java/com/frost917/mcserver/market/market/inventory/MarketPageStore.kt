package com.frost917.mcserver.market.market.inventory

import com.frost917.mcserver.market.market.MarketType
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.*

object MarketPageStore {
    private var pageMap = mutableMapOf<UUID, MarketInventory>()

    fun openNewMarket(player: Player) {
        val UUID = player.playerProfile.id ?: throw Exception("Player Data transfer error occurred!")

        pageMap[UUID!!] = MarketInventory(player)
        pageMap[UUID!!]?.let{player.openInventory(it.getInventory())}
    }

    fun getMarketPage(player: Player): Inventory {
        val UUID = player.playerProfile.id
        if (pageMap[UUID!!] == null) {
            pageMap[UUID] = MarketInventory(player)
        }

        return pageMap[UUID]!!.getInventory()
    }

    fun closeMarketPage(player: Player) {
        val UUID = player.playerProfile.id
        pageMap.remove(UUID)
    }
}