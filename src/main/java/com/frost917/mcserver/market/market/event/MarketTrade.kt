package com.frost917.mcserver.market.market.event

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.market.inventory.MarketPageStore
import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.storage.StorageManager
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack

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

        val clickedItem = event.currentItem ?: return
        val item = clickedItem.type
        // 아무 아이템도 클릭 안했을 경우
        if (item.isAir) {
            return
        }

        val playerInventory = event.whoClicked.inventory
        // 플레이어의 아이템 칸이 꽉 차있으면 무시
        if (playerInventory.first(item) != -1 ||playerInventory.firstEmpty() == -1) {
            event.whoClicked.sendMessage("경고. 인벤토리가 가득 찼습니다.\n타인이 아이템을 먹을 수도 있습니다.")
        }

        val itemData = storage.getMarketData(item)
        val saleData = ItemValueManager.calcItemValue(itemData)
        var result: ItemData
        var tradeQuantity = 0

        // 왼쪽 클릭시 1개 구매
        if(event.isLeftClick) {
            tradeQuantity = 1
        }
            /*
        // 오른쪽 클릭시 원하는 개수만큼 구매
        else if (event.isRightClick) {

        }
             */
        // 시프트 클릭시 maxQuantity만큼 구매
        else if (event.isShiftClick) {
            tradeQuantity = saleData.getStackQuantity()
        }

        result = ItemValueManager.tradeItem(itemData, tradeQuantity)
        val newItem = ItemStack(item, tradeQuantity)
        val playerLocation = event.whoClicked.location
        var customLocation = Location(playerLocation.world, playerLocation.x, playerLocation.y + 3, playerLocation.z)

        playerLocation.world.dropItem(customLocation, newItem)

        storage.syncItemData(result)

        /* 차후 개선용 코드
        /* 아이템 거래 과정
         * 1. 아이템 클릭
         * 2. 인벤토리에 해당 아이템이 있는지 확인
         * 해당 아이템이 있는 경우 maxStackSize보다 적은지 확인
         * maxStackSize보다 적은 경우 해당 아이템 스택에 추가
         * 3. 기존에 존재하는 아이템 스택에 추가 불가능한 경우 새 스택 추가
         * 2-3 과정 반복
         */

         // 빈 공간 및 아이템을 더 넣을 수 있는 공간

        val notMaxStackSlot = HashMap<Int, ItemStack>()
        itemStackSlot.forEach {
            if(it.value.amount < it.value.maxStackSize) {
                notMaxStackSlot[it.key] = it.value
            }
        }

        // 임시 저장용 해시맵
        val newItemStack = hashMapOf<Int, ItemStack>()

        // 빈 공간이 있는 아이템 스택이 없어질 때까지 인벤토리에 집어넣기
        for(stack in notMaxStackSlot) {
            if (tradeQuantity + stack.value.amount.toUInt() < stack.value.maxStackSize.toUInt()) {
                // 스택에 집어넣고 나면 집어넣은 양 만큼 제거
                tradeQuantity -= ( stack.value.maxStackSize - stack.value.amount ).toUInt()

                stack.value.add(tradeQuantity.toInt())

                // 거래가 끝나면 그냥 탈출
                if(tradeQuantity <= 0u) break
            }
        }

        // tradeQuantity가 0이 될 때까지 새 아이템 스택 생성
        while(tradeQuantity <= 0) {
            val insertedItemNum = tradeQuantity -
            newItemStack[playerInventory.firstEmpty()] = ItemStack(item, )
        }
         */
    }

    // 상점 창 닫을시 이 이벤트는 정리
    @EventHandler
    fun closeMarketInventory(event: InventoryCloseEvent) {
        HandlerList.unregisterAll(this)
        MarketPageStore.syncMarketPage(event.player as Player, 0)
    }
}