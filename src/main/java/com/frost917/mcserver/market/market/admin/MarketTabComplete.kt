package com.frost917.mcserver.market.market.admin

import com.frost917.mcserver.market.Main
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MarketTabComplete: TabCompleter {
    val storage = Main.MainPlugin.getStorage()

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        val registeredList = mutableListOf<Material>()
        storage.getMarketData().forEach {
            registeredList.add(it.material)
        }

        val notRegisteredList = enumValues<Material>().toMutableList()
        notRegisteredList.forEach {
            if (registeredList.contains(it)) {
                notRegisteredList.remove(it)
            }
        }

        val result = mutableListOf<String>()
        notRegisteredList.forEach {
            result.add(it.toString())
        }

        return result
    }
}