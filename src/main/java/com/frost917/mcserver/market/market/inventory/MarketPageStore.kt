package com.frost917.mcserver.market.market.inventory

import org.bukkit.entity.Player
import java.util.*

object MarketPageStore {
    private var pageMap = mutableMapOf<UUID, Int>()

    fun syncMarketPage(player: Player, page: Int) {
        val UUID = player.playerProfile.id
        pageMap[UUID!!] = page
    }

    fun getMarketPage(player: Player): Int {
        val UUID = player.playerProfile.id
        if (pageMap[UUID!!] == null) {
            pageMap[UUID!!] = 0
        }

        return pageMap[UUID!!]!!
    }
}