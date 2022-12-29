package com.github.sqyyy.cougar.impl.panel

import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView

class TakeableSlotEventPanel(
    slot: Int,
    private val takeCallback: (player: Player, view: InventoryView, slot: Int) -> Boolean,
) : AbstractSlotPanel(slot) {
    override fun take(player: Player, view: InventoryView, slot: Int): Boolean {
        return this.takeCallback(player, view, slot)
    }

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = true
}