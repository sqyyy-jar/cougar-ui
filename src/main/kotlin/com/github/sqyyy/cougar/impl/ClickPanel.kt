package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView

class ClickPanel(
    private val startSlot: Int,
    private val endSlot: Int,
    private val callback: (player: Player, view: InventoryView, slot: Int) -> Boolean,
    private val type: InventoryType,
) : Panel {
    private val scale = if (this.type == InventoryType.CHEST) 9 else 3
    private val startColumn = this.startSlot % this.scale
    private val startRow = this.startSlot - this.startColumn
    private val endColumn = this.endSlot % this.scale
    private val endRow = this.endSlot - this.endColumn

    override fun collidesWith(slot: Int): Boolean = this.startSlot <= slot && slot <= this.endSlot

    override fun click(player: Player, view: InventoryView, slot: Int): Boolean {
        return this.callback(player, view, slot)
    }

    override fun canClick(slot: Int): Boolean = true

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false
}