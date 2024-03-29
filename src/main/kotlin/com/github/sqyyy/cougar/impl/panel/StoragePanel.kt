package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Cougar
import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class StoragePanel(
    private val startSlot: Int,
    private val endSlot: Int,
    private val rowWidth: Int,
    private val updateCallback: (Player, InventoryView) -> Unit = { _, _ -> },
) : Panel {
    private val startRow = Slot.getRow(this.rowWidth, this.startSlot)
    private val startColumn = Slot.getColumn(this.rowWidth, this.startSlot)
    private val endRow = Slot.getRow(this.rowWidth, this.endSlot)
    private val endColumn = Slot.getColumn(this.rowWidth, this.endSlot)

    override fun collidesWith(slot: Int): Boolean {
        val column: Int = Slot.getColumn(this.rowWidth, slot)
        val row: Int = Slot.getRow(this.rowWidth, slot)
        return row in this.startRow..this.endRow && column in this.startColumn..this.endColumn
    }

    override fun place(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        Cougar.scheduleTask { updateCallback(player, view) }
        return false
    }

    override fun placeMany(player: Player, view: InventoryView, items: Map<Int, ItemStack>): Boolean {
        Cougar.scheduleTask { updateCallback(player, view) }
        return false
    }

    override fun take(player: Player, view: InventoryView, slot: Int): Boolean {
        Cougar.scheduleTask { updateCallback(player, view) }
        return false
    }

    override fun replace(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        Cougar.scheduleTask { updateCallback(player, view) }
        return false
    }

    override fun open(player: Player, inventory: Inventory) {
        for (row in this.startRow..this.endRow) {
            for (column in this.startColumn..this.endColumn) {
                inventory.setItem(row * this.rowWidth + column, null)
            }
        }
    }

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = true

    override fun canTake(slot: Int): Boolean = true
}