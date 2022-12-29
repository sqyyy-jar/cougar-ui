package com.github.sqyyy.cougar.impl.panel

import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView

class SlotClickEventPanel(
    slot: Int,
    private val clickCallback: (player: Player, view: InventoryView, slot: Int) -> Unit,
) : AbstractSlotPanel(slot) {
    override fun click(player: Player, view: InventoryView, slot: Int) = this.clickCallback(player, view, slot)

    override fun canClick(slot: Int): Boolean = true

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false
}