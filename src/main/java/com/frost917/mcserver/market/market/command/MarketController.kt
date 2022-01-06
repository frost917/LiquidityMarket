package com.frost917.mcserver.market.market.command

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import com.frost917.mcserver.market.market.event.MarketTrade
import com.frost917.mcserver.market.market.inventory.MarketInventoryManager
import com.frost917.mcserver.market.market.inventory.MarketListManager
import com.frost917.mcserver.market.market.inventory.MarketPageStore
import com.frost917.mcserver.market.market.itemManager.ItemValueManager
import com.frost917.mcserver.market.storage.StorageManagerFactory
import com.frost917.mcserver.market.storage.StorageType
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player

/*
 * market 커맨드 관련 처리
 */
class MarketController(
    private val storageType: StorageType
): TabExecutor {
    val storage = StorageManagerFactory.getStorage(storageType)

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender Source of the command.  For players tab-completing a
     * command inside of a command block, this will be the player, not
     * the command block.
     * @param command Command which was executed
     * @param alias The alias used
     * @param args The arguments passed to the command, including final
     * partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    /**
     * Executes the given command, returning its success.
     * <br></br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender is Player) {
            if (args[0] == "open") {
                // 퍼미션 여부 확인
                if (!(sender.hasPermission("market.*") || sender.hasPermission("market.open"))) {
                    return false
                }

                val itemList = storage.getMarketData()

                // 거래소 페이지는 0으로 초기화
                MarketPageStore.syncMarketPage(sender, 0)

                val splitItemList = MarketListManager.splitMarketList(
                    itemList,
                    MarketPageStore.getMarketPage(
                        sender
                    )
                )
                val marketInventory = MarketInventoryManager.drawMarketInventory(
                    MarketListManager.convertItemToSale(
                        splitItemList
                    )
                )
                // 플레이어에게 인벤토리 창 보여주게 해야됨
                sender.openInventory(marketInventory)

                // 아이템 클릭 이벤트 처리용 이벤트 레지스터
                val plugin = Main.MainPlugin.getPlugin()
                Bukkit.getPluginManager().registerEvents(MarketTrade(storage), plugin)
                return true
            }
            // info의 경우 도움말 혹은 특정 아이템의 시세 불러옴
            else if (args[0] == "info") {
                if (args.count() < 2) {
                    sender.chat("/market open으로 상점 페이지를 열 수 있습니다." +
                            "/market info <item>으로 해당 아이템의 가격을 알 수 있습니다.")
                }
                else {
                    // 인자로 들어온 데이터를 검증, 설명 출력
                    args.forEach {
                        val material = Material.getMaterial(it)
                        // Material.AIR은 해당 아이템이 없는 경우의 결과
                        if (material != null && material != Material.AIR) {
                            val saleData = ItemValueManager.calcItemValue(storage.getMarketData(material))
                            sender.chat(saleData.toLore().toString())
                        }
                    }
                }
            }
        }

        return false
    }

}