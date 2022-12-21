package com.github.sqyyy.cougar.internal

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryCreativeEvent
import org.bukkit.event.inventory.InventoryDragEvent

class SystemListener : Listener {
    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        val holder = event.view.topInventory.holder
        if (holder is UiHolder) {
            holder.onClose(event)
        }
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {
        val holder = event.view.topInventory.holder
        if (holder is UiHolder) {
            holder.onDrag(event)
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val holder = event.view.topInventory.holder
        if (holder is UiHolder) {
            holder.onClick(event, event is InventoryCreativeEvent)
        }
    }
}