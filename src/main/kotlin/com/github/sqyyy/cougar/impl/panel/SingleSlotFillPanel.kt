package com.github.sqyyy.cougar.impl.panel

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SingleSlotFillPanel(slot: Int, private val fillItem: ItemStack?) : AbstractSlotPanel(slot) {
    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, inventory: Inventory) {
        inventory.setItem(this.slot, this.fillItem)
    }
}