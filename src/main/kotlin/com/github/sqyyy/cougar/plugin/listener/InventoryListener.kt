package com.github.sqyyy.cougar.plugin.listener

import com.github.sqyyy.cougar.plugin.CougarDatasetPlugin
import com.github.sqyyy.cougar.plugin.Entry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent

class InventoryListener(private val cougar: CougarDatasetPlugin) : Listener {
    @EventHandler
    fun on(event: InventoryClickEvent) {
        event.whoClicked.sendMessage(
            """
Event: ${event.eventName}:${event.action}:${event.rawSlot}:${event.currentItem?.type}:${event.cursor?.type}
            """.trim()
        )
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(
            event, event.eventName, event.action, event.click, event.slotType, event.isLeftClick,
            event.isRightClick, event.isShiftClick
        )
        dataset += entry
    }

    @EventHandler
    fun on(event: InventoryDragEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(event, event.eventName, dragType = event.type)
        dataset += entry
    }
}