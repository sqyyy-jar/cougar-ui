package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView

class SingleSlotClickPanel(
    private val slot: Int,
    private val callback: (player: Player, view: InventoryView, slot: Int) -> Unit,
) : Panel {
    override fun collidesWith(slot: Int): Boolean = slot == this.slot

    override fun click(player: Player, view: InventoryView, slot: Int) = this.callback(player, view, slot)

    override fun canClick(slot: Int): Boolean = true

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false
}