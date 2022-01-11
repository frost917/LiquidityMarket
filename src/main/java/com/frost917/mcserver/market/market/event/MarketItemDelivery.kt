package com.frost917.mcserver.market.market.event

import com.frost917.mcserver.market.Main
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityPickupItemEvent
import org.bukkit.persistence.PersistentDataType

class MarketItemDelivery: Listener {
    @EventHandler
    fun alertPickupAnotherPlayer(event: EntityPickupItemEvent) {

        val player = event.entity as Player
        val item = event.item
        val persistentDataContainer = item.persistentDataContainer
        val key = Main.MainPlugin.getNamespacedKey()

        // 구매한 아이템에 한정
        if(persistentDataContainer.get(key, PersistentDataType.STRING) == "BoughtItem") {
            // 아이템 주운 사람이 주인이 아니면 메시지 띄움
            if(item.owner != player.playerProfile.id) {
                val ownerPlayer =  Bukkit.getPlayer(item.owner!!)!!
                val pickupPlayerName = player.name

                ownerPlayer.sendMessage("${pickupPlayerName}님이 당신의 아이템을 먹었습니다!")
            } else {
                return
            }
        // 상점에서 구매한 아이템이 아닌 경우 무시
        } else {
            return
        }
    }
}