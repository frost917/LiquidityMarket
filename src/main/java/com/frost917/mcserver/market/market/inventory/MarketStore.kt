package com.frost917.mcserver.market.market.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.util.*

object MarketStore {
    private var pageMap = mutableMapOf<UUID, MarketInventory>()

    fun openNewMarket(player: Player) {
        val UUID = player.playerProfile.id ?: throw Exception("Player Data transfer error occurred!")

        pageMap[UUID] = MarketInventory(player)
        pageMap[UUID]?.let{player.openInventory(it.getInventory())}
    }

    fun isMarketOpen(player: Player): Boolean {
        val UUID = player.playerProfile.id ?: throw Exception("Player Data transfer error occurred!")
        return pageMap.containsKey(UUID)
    }

    fun getMarketPage(player: Player): Inventory {
        val UUID = player.playerProfile.id ?: throw Exception("Player Data transfer error occurred!")
        if (pageMap[UUID] == null) {
            pageMap[UUID] = MarketInventory(player)
        }

        return pageMap[UUID]!!.getInventory()
    }

    fun closeMarketPage(player: Player) {
        val UUID = player.playerProfile.id ?: throw Exception("Player Data transfer error occurred!")
        pageMap.remove(UUID)
    }
}