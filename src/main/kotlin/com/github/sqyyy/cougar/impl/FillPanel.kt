package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class FillPanel(
    private val startSlot: Int, private val endSlot: Int, private val fillItem: ItemStack,
    private val type: InventoryType,
) : Panel {
    private val scale = if (this.type == InventoryType.CHEST) 9 else 3
    private val startColumn = this.startSlot % this.scale
    private val startRow = this.startSlot - this.startColumn
    private val endColumn = this.endSlot % this.scale
    private val endRow = this.endSlot - this.endColumn

    override fun collidesWith(slot: Int): Boolean = this.startSlot <= slot && slot <= this.endSlot

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, view: InventoryView) {
        val inventory = view.topInventory
        when (inventory.type) {
            InventoryType.CHEST -> {
                for (row in this.startRow..this.endRow) {
                    for (column in this.startColumn..this.endColumn) {
                        inventory.setItem(row * 9 + column, this.fillItem)
                    }
                }
            }
            InventoryType.DISPENSER, InventoryType.DROPPER -> {
                for (row in this.startRow..this.endRow) {
                    for (column in this.startColumn..this.endColumn) {
                        inventory.setItem(row * 3 + column, this.fillItem)
                    }
                }
            }
            InventoryType.HOPPER -> {
                val start = this.startSlot % 5
                val end = this.endSlot % 5
                for (i in start..end) {
                    inventory.setItem(i, this.fillItem)
                }
            }
            else -> {
                throw IllegalArgumentException("Unsupported InventoryType was provided")
            }
        }
    }
}