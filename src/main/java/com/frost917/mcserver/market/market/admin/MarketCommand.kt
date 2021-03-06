package com.frost917.mcserver.market.market.admin

import com.frost917.mcserver.market.ItemData
import com.frost917.mcserver.market.Main
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MarketCommand: CommandExecutor {
    val storage = Main.MainPlugin.getStorage()
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
        if(sender is Player && sender.hasPermission("market.admin")) {
            if (args[0] == "add") {
                // 커맨드를 자료형에 맞춰 변환
                val material: Material = Material.getMaterial(args[1]) ?: throw Exception("Material ${args[1]} is not found!")
                val marketValue: Long = args[2].toLong()
                val totalQuantity: Int = args[3].toInt()
                val newItem = ItemData(material, marketValue, totalQuantity)

                return storage.addItemData(newItem)
            }
        }
        return false
    }
}