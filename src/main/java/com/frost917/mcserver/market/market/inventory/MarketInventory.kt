package com.frost917.mcserver.market.market.inventory

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.market.MarketType
import com.frost917.mcserver.market.market.event.MarketTrade
import com.frost917.mcserver.market.market.itemManager.SaleData
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

// open market
class MarketInventory(player: Player) {
    // TODO) 마켓 리스트는 MarketListManager에서 loadMarketList(marketPage) 메소드로 불러올 것
    private val plugin = Main.MainPlugin.getPlugin()
    private lateinit var marketList: List<SaleData>

    private val inventory = Bukkit.createInventory(player, 54, Component.text("자유시장"))
    private var marketPage = 1
    private lateinit var marketType: MarketType

    init {
        relocateItemSlot()
    }

    // refresh ItemList
    private fun refreshMarketData() {
        // get market data
        marketList = MarketStore.loadMarketData(marketPage)
    }

    private fun relocateItemSlot() {

        if (marketList.count() >= 46) {
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

        inventory.setItem(45, prevPage)

        val nextPage = ItemStack(Material.GREEN_STAINED_GLASS_PANE)
        val nextPageMeta = prevPage.itemMeta
        val nextPageLore = mutableListOf<Component>()

        nextPageLore.add(Component.text("다음 페이지로 넘어갑니다."))
        nextPageMeta.displayName(Component.text("다음 페이지로"))
        nextPageMeta.lore(prevPageLore)

        inventory.setItem(53, nextPage)
    }

    fun prevPage() {
        marketPage -= 1
        relocateItemSlot()
    }

    fun nextPage() {
        marketPage += 1
        relocateItemSlot()
    }

    fun getInventory(): Inventory {
        return inventory
    }

    fun setMarketType(type: MarketType) {
        marketType = type
    }
    fun getMarketType(type: MarketType): MarketType {
        return marketType
    }
}
