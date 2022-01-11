package com.frost917.mcserver.market

import com.frost917.mcserver.market.market.admin.MarketCommand
import com.frost917.mcserver.market.market.admin.MarketTabComplete
import com.frost917.mcserver.market.market.command.MarketController
import com.frost917.mcserver.market.market.event.MarketItemDelivery
import com.frost917.mcserver.market.storage.StorageManager
import com.frost917.mcserver.market.storage.StorageManagerFactory
import com.frost917.mcserver.market.storage.StorageType
import org.bukkit.NamespacedKey
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        getCommand("market")?.setExecutor(MarketController())
        getCommand("marketadmin")?.setExecutor(MarketCommand())
        getCommand("marketadmin")?.tabCompleter = MarketTabComplete()
        server.pluginManager.registerEvents(MarketItemDelivery(), this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }

    object MainPlugin {
        private val storage: StorageManager = StorageManagerFactory.getStorage(getStorageType())
        private val namespacedKey = NamespacedKey(getPlugin(), "LiquidityMarket")

        fun getPlugin(): JavaPlugin {
            return getPlugin()
        }

        fun getStorageType(): StorageType {
            return StorageType.valueOf(getPlugin().config.getString("db_type")!!)
        }

        fun getStorage(): StorageManager {
            return this.storage
        }

        fun getNamespacedKey(): NamespacedKey {
            return namespacedKey
        }
    }


}