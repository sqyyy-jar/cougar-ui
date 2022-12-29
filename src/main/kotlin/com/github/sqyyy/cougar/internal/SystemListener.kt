package com.github.sqyyy.cougar.internal

import com.github.sqyyy.cougar.Cougar
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
            Cougar.UnsafeValues.inventoryCloseEvent = event
            holder.onClose(event)
            Cougar.UnsafeValues.inventoryCloseEvent = null
        }
    }

    @EventHandler
    fun onDrag(event: InventoryDragEvent) {
        val holder = event.view.topInventory.holder
        if (holder is UiHolder) {
            Cougar.UnsafeValues.inventoryDragEvent = event
            holder.onDrag(event)
            Cougar.UnsafeValues.inventoryDragEvent = null
        }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val holder = event.view.topInventory.holder
        if (holder is UiHolder) {
            Cougar.UnsafeValues.inventoryClickEvent = event
            holder.onClick(event, event is InventoryCreativeEvent)
            Cougar.UnsafeValues.inventoryClickEvent = null
        }
    }
}