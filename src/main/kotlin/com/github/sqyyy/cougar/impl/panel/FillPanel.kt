package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class FillPanel(
    private val startSlot: Int, private val endSlot: Int, private val rowWidth: Int, private val fillItem: ItemStack,
) : Panel {
    private val startRow = Slot.getRow(this.rowWidth, this.startSlot)
    private val startColumn = Slot.getColumn(this.rowWidth, this.startSlot)
    private val endRow = Slot.getRow(this.rowWidth, this.endSlot)
    private val endColumn = Slot.getColumn(this.rowWidth, this.endSlot)

    override fun collidesWith(slot: Int): Boolean {
        val column: Int = Slot.getColumn(this.rowWidth, slot)
        val row: Int = Slot.getColumn(this.rowWidth, slot)
        return row in this.startRow..this.endRow && column >= this.startColumn && column <= this.endColumn
    }

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, inventory: Inventory) {
        for (row in this.startRow..this.endRow) {
            for (column in this.startColumn..this.endColumn) {
                inventory.setItem(row * this.rowWidth + column, this.fillItem)
            }
        }
    }
}