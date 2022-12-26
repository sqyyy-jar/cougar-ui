package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class FrameFillPanel(
    private val startSlot: Int, private val endSlot: Int, private val rowWidth: Int, private val fillItem: ItemStack,
) : Panel {
    private val startRow = Slot.getRow(this.rowWidth, this.startSlot)
    private val startColumn = Slot.getColumn(this.rowWidth, this.startSlot)
    private val endRow = Slot.getRow(this.rowWidth, this.endSlot)
    private val endColumn = Slot.getColumn(this.rowWidth, this.endSlot)

    override fun collidesWith(slot: Int): Boolean {
        val row: Int = Slot.getRow(rowWidth, slot)
        val column: Int = Slot.getColumn(rowWidth, slot)
        return (row == startRow || row == endRow) && (column == startColumn || column == endColumn)
    }

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, inventory: Inventory) {
        for (column in startColumn..endColumn) {
            inventory.setItem(startRow * this.rowWidth + column, this.fillItem)
        }
        for (row in startRow + 1 until endRow) {
            inventory.setItem(row * this.rowWidth + startColumn, this.fillItem)
            inventory.setItem(row * this.rowWidth + endColumn, this.fillItem)
        }
        for (column in startColumn..endColumn) {
            inventory.setItem(endRow * this.rowWidth + column, this.fillItem)
        }
    }
}