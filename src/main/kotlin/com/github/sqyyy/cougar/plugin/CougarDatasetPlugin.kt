package com.github.sqyyy.cougar.plugin

import com.github.sqyyy.cougar.plugin.listener.InventoryListener
import org.bukkit.plugin.java.JavaPlugin

class CougarDatasetPlugin : JavaPlugin() {
    override fun onEnable() {
        server.pluginManager.also {
            it.registerEvents(InventoryListener(this), this)
        }
    }
}