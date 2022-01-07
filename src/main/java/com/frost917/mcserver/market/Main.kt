package com.frost917.mcserver.market

import com.frost917.mcserver.market.market.command.MarketController
import com.frost917.mcserver.market.storage.StorageType
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        getCommand("market")?.setExecutor(MarketController(MainPlugin.getStorageType()))
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    object MainPlugin {
        fun getPlugin(): JavaPlugin {
            return getPlugin()
        }
        fun getStorageType(): StorageType {
            return StorageType.valueOf(getPlugin().config.getString("db_type")!!)
        }
    }


}