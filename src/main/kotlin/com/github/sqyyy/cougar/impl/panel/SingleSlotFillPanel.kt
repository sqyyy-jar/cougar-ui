package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class SingleSlotFillPanel(private val slot: Int, private val fillItem: ItemStack?) : Panel {
    override fun collidesWith(slot: Int): Boolean = slot == this.slot

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, inventory: Inventory) {
        inventory.setItem(this.slot, this.fillItem)
    }
}