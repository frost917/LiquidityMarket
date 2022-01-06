package com.frost917.mcserver.market.market.inventory

import com.frost917.mcserver.market.market.itemManager.SaleData
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object MarketInventoryManager {
    fun drawMarketInventory(marketList: List<SaleData>): Inventory {
        val inventory = Bukkit.createInventory(null, 48, Component.text("거래소"))

        // 사전에 정해진 개수로 분할된 리스트만 받음
        if (inventory.count() > 36) {
            throw Exception("Market list shouldn't over than 36 items!")
        }

        marketList.forEach {
            // 아이템에 가격 정보 추가
            val item = ItemStack(it.material)
            item.lore(it.toLore())

            // 인벤토리에 새 아이템 추가
            inventory.addItem(item)
        }

        val prevPage = ItemStack(Material.RED_STAINED_GLASS_PANE)
        val prevPageMeta = prevPage.itemMeta
        val prevPageLore = mutableListOf<Component>()

        prevPageLore.add(Component.text("이전 페이지로 돌아갑니다."))
        prevPageMeta.displayName(Component.text("이전 페이지로"))
        prevPageMeta.lore(prevPageLore)

        inventory.setItem(27, prevPage)

        val nextPage = ItemStack(Material.GREEN_STAINED_GLASS_PANE)
        val nextPageMeta = prevPage.itemMeta
        val nextPageLore = mutableListOf<Component>()

        nextPageLore.add(Component.text("다음 페이지로 넘어갑니다."))
        nextPageMeta.displayName(Component.text("다음 페이지로"))
        nextPageMeta.lore(prevPageLore)

        inventory.setItem(35, nextPage)

        return inventory
    }
}