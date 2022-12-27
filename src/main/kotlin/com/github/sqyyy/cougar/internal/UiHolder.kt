package com.github.sqyyy.cougar.internal

import com.github.sqyyy.cougar.Ui
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

class UiHolder(private val ui: Ui) : InventoryHolder {
    private var inventory: Inventory? = null

    override fun getInventory(): Inventory = this.inventory!!

    fun setInventory(inventory: Inventory) {
        if (this.inventory != null) {
            throw IllegalStateException("Inventory already set")
        }
        this.inventory = inventory
    }

    fun onClose(event: InventoryCloseEvent) {
        ui.close(event.player as Player, event.view, event.reason)
    }

    fun onDrag(event: InventoryDragEvent) {
        if (event.rawSlots.any { it < ui.slots && !ui.canPlace(it) }) {
            event.isCancelled = true
            return
        }
        event.isCancelled = ui.placeMany(event.whoClicked as Player, event.view, event.newItems.filter { it.key < ui.slots }
            .toMap())
    }

    fun onClick(event: InventoryClickEvent, @Suppress("UNUSED_PARAMETER") creative: Boolean) {
        if (this.inventory == null) {
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
                    if (ui.canClick(slot)) {
                        ui.click(event.whoClicked as Player, event.view, slot)
                    }
                    return
                }
                event.isCancelled = ui.take(event.whoClicked as Player, event.view, slot)
            }
            InventoryAction.PLACE_ALL,
            InventoryAction.PLACE_SOME,
            InventoryAction.PLACE_ONE,
            -> {
                if (event.cursor == null) {
                    return
                }
                if (!uiClick) {
                    return
                }
                if (!ui.canPlace(slot)) {
                    event.isCancelled = true
                    if (ui.canClick(slot)) {
                        ui.click(event.whoClicked as Player, event.view, slot)
                    }
                    return
                }
                event.isCancelled = ui.place(event.whoClicked as Player, event.view, slot, event.cursor!!)
            }
            InventoryAction.SWAP_WITH_CURSOR,
            InventoryAction.HOTBAR_MOVE_AND_READD,
            -> {
                if (!uiClick) {
                    return
                }
                if (!ui.canTake(slot) || !ui.canPlace(slot)) {
                    event.isCancelled = true
                    if (ui.canClick(slot)) {
                        ui.click(event.whoClicked as Player, event.view, slot)
                    }
                    return
                }
                event.isCancelled = ui.replace(event.whoClicked as Player, event.view, slot, event.currentItem!!)
            }
            InventoryAction.HOTBAR_SWAP -> {
                if (!uiClick) {
                    return
                }
                val place = event.currentItem == null || event.currentItem!!.type == Material.AIR
                if (place) {
                    if (!ui.canPlace(slot)) {
                        event.isCancelled = true
                        if (ui.canClick(slot)) {
                            ui.click(event.whoClicked as Player, event.view, slot)
                        }
                        return
                    }
                    if (event.currentItem == null) {
                        event.isCancelled = ui.place(
                            event.whoClicked as Player, event.view, slot, event.whoClicked.inventory.getItem(event.hotbarButton)!!
                        )
                        return
                    }
                    event.isCancelled = ui.take(event.whoClicked as Player, event.view, slot)
                    return
                }
                if (!ui.canTake(slot)) {
                    event.isCancelled = true
                    if (ui.canClick(slot)) {
                        ui.click(event.whoClicked as Player, event.view, slot)
                    }
                    return
                }
                event.isCancelled = ui.take(event.whoClicked as Player, event.view, slot)
            }
            InventoryAction.MOVE_TO_OTHER_INVENTORY -> {
                if (event.currentItem == null) {
                    return
                }
                val currentItem = event.currentItem!!
                if (currentItem.type == Material.AIR) {
                    return
                }
                if (uiClick) {
                    if (!ui.canTake(slot)) {
                        event.isCancelled = true
                        if (ui.canClick(slot)) {
                            ui.click(event.whoClicked as Player, event.view, slot)
                        }
                        return
                    }
                    event.isCancelled = ui.take(event.whoClicked as Player, event.view, slot)
                    return
                }
                val inventory = this.inventory!!
                var amount = currentItem.amount
                event.isCancelled = true
                for (i in 0 until ui.slots) {
                    if (!ui.canPlace(i)) {
                        continue
                    }
                    val item = inventory.getItem(i)
                    if (item == null || item.type == Material.AIR) {
                        event.view.bottomInventory.setItem(event.slot, null)
                        inventory.setItem(i, currentItem)
                        break
                    }
                    if (item.amount >= 64) {
                        continue
                    }
                    if (currentItem.isSimilar(item)) {
                        val max = 64 - item.amount
                        if (max >= amount) {
                            event.view.bottomInventory.setItem(event.slot, null)
                            inventory.setItem(i, currentItem)
                            break
                        }
                        amount -= max
                        item.amount = 64
                        currentItem.amount = amount
                        break
                    }
                }
            }
            InventoryAction.COLLECT_TO_CURSOR,
            InventoryAction.UNKNOWN,
            -> {
                event.isCancelled = true
            }
            else -> {}
        }
    }
}