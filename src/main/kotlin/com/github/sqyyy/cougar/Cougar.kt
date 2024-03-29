package com.github.sqyyy.cougar

import com.github.sqyyy.cougar.internal.SystemListener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.plugin.Plugin

object Cougar {
    private var initialized = false
    private var plugin: Plugin? = null

    @JvmStatic
    fun initializeSystem(plugin: Plugin) {
        if (initialized) {
            throw IllegalStateException("Cougar is already initialized")
        }
        this.plugin = plugin
        plugin.server.pluginManager.run {
            registerEvents(SystemListener(), plugin)
        }
        initialized = true
    }

    @JvmStatic
    internal fun scheduleTask(task: Runnable) {
        if (plugin == null) {
            throw IllegalStateException("Cougar is not yet initialized")
        }
        plugin!!.server.scheduler.runTask(plugin!!, task)
    }

    object UnsafeValues {
        @JvmStatic
        var inventoryCloseEvent: InventoryCloseEvent? = null
        @JvmStatic
        var inventoryDragEvent: InventoryDragEvent? = null
        @JvmStatic
        var inventoryClickEvent: InventoryClickEvent? = null
    }
}