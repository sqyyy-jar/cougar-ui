package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class SingleSlotFillPanel(private val slot: Int, private val fillItem: ItemStack) : Panel {
    override fun collidesWith(slot: Int): Boolean = slot == this.slot

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, view: InventoryView) {
        view.topInventory.setItem(this.slot, this.fillItem)
    }
}