package com.github.sqyyy.cougar.internal

import com.github.sqyyy.cougar.Ui
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class UiHolder(private val ui: Ui) : InventoryHolder {
    private var inventory: Inventory? = null

    fun setInventory(inventory: Inventory) {
        if (this.inventory != null) {
            throw IllegalStateException("Inventory already set")
        }
        this.inventory = inventory
    }

    override fun getInventory(): Inventory = inventory!!

    fun onClose(event: InventoryCloseEvent) {
        ui.close(event.player as Player, event.reason)
    }

    fun onDrag(event: InventoryDragEvent) {
        if (event.rawSlots.any { it < ui.slots && !ui.canPlace(it) }) {
            event.isCancelled = true
            return
        }
        ui.placeMany(event.rawSlots.filter { it < ui.slots }.toSet())
    }

    fun onClick(event: InventoryClickEvent, @Suppress("UNUSED_PARAMETER") creative: Boolean) {
        if (inventory == null) {
            throw IllegalStateException("No inventory set")
        }
        val slot = event.rawSlot
        val uiClick = slot < ui.slots
        when (event.action) {
            InventoryAction.PICKUP_ALL,
            InventoryAction.PICKUP_SOME,
            InventoryAction.PICKUP_HALF,
            InventoryAction.PICKUP_ONE,
            InventoryAction.DROP_ALL_SLOT,
            InventoryAction.DROP_ONE_SLOT,
            -> {
                if (!uiClick) {
                    return
                }
                if (!ui.canTake(slot)) {
                    event.isCancelled = true
                    return
                }
                ui.take(slot)
            }
            InventoryAction.PLACE_ALL,
            InventoryAction.PLACE_SOME,
            InventoryAction.PLACE_ONE,
            -> {
                if (!uiClick) {
                    return
                }
                if (!ui.canPlace(slot)) {
                    event.isCancelled = true
                    return
                }
                ui.place(slot)
            }
            InventoryAction.SWAP_WITH_CURSOR,
            InventoryAction.HOTBAR_MOVE_AND_READD,
            InventoryAction.HOTBAR_SWAP,
            -> {
                if (!uiClick) {
                    return
                }
                if (!ui.canTake(slot) || !ui.canPlace(slot)) {
                    event.isCancelled = true
                    return
                }
                ui.replace(slot)
            }
            InventoryAction.MOVE_TO_OTHER_INVENTORY -> {
                if (event.currentItem == null) {
                    return
                }
                if (uiClick) {
                    if (!ui.canTake(slot)) {
                        event.isCancelled = true
                        return
                    }
                    ui.take(slot)
                    return
                }
                val nextSlot = ui.nextPlaceableSlot(inventory!!, event.currentItem!!)
                if (nextSlot == -1) {
                    event.isCancelled = true
                    return
                }
                TODO("Find next placeable slot and put item into it")
            }
            InventoryAction.COLLECT_TO_CURSOR -> {
                event.isCancelled = true
            }
            InventoryAction.UNKNOWN -> TODO("Maybe cancel this for now")
            else -> {}
        }
    }
}