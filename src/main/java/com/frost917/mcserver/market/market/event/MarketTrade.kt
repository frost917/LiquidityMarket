package com.frost917.mcserver.market.market.event

import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.market.inventory.MarketInventoryManager
import com.frost917.mcserver.market.market.inventory.MarketPageStore
import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.storage.StorageManager
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType

class MarketTrade(
    private val storage: StorageManager
): Listener{

    // 이론상 상점 창이 열려있을 때에만 작동
    @EventHandler
    fun marketItemClick(event: InventoryClickEvent) {
        // 디버그용 코드
        Main.MainPlugin.getPlugin().logger.info("LQMarket: Item Clicked!")

        // 플레이어 인벤토리 클릭시 무시
        if (event.inventory.type == InventoryType.PLAYER) {
            return
        }

        // 아무 아이템도 클릭 안했을 경우
        if (event.currentItem != null && event.currentItem!!.type == Material.AIR) {
            return
        }

        // 왼쪽 클릭시 1개 구매
        if(event.isLeftClick) {
            val item = event.currentItem!!
            val saleData = ItemValueManager.calcItemValue(storage.getMarketData(item.type))
        }
        // 오른쪽 클릭시 원하는 개수만큼 구매
        // 시프트 클릭시 maxQuantity만큼 구매
    }

    // 상점 창 닫을시 이 이벤트는 정리
    @EventHandler
    fun closeMarketInventory(event: InventoryCloseEvent) {
        HandlerList.unregisterAll(this)
        MarketPageStore.syncMarketPage(event.player as Player, 0)
    }
}