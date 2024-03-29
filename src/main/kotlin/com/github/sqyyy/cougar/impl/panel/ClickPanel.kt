package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Slot
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView

class ClickPanel(
    private val startSlot: Int,
    private val endSlot: Int,
    private val rowWidth: Int,
    private val clickCallback: (player: Player, view: InventoryView, slot: Int) -> Unit,
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

    override fun click(player: Player, view: InventoryView, slot: Int) = this.clickCallback(player, view, slot)

    override fun canClick(slot: Int): Boolean = true

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false
}