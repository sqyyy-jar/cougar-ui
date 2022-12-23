package com.github.sqyyy.cougar.plugin.listener

import com.github.sqyyy.cougar.plugin.CougarDatasetPlugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class InventoryListener(private val cougar: CougarDatasetPlugin) : Listener {
    @EventHandler
    fun on(event: InventoryClickEvent) {
        event.whoClicked.sendMessage(
            """
Event: ${event.eventName}:${event.action}:${event.rawSlot}:${event.currentItem?.type}:${event.cursor?.type}
            """.trim()
        )
    }
}