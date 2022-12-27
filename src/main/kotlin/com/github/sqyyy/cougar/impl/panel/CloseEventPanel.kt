package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.InventoryView

class CloseEventPanel(private val closeCallback: (Player, InventoryView, InventoryCloseEvent.Reason) -> Unit) : Panel {
    override fun collidesWith(slot: Int): Boolean = false

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun close(player: Player, view: InventoryView, reason: InventoryCloseEvent.Reason) =
        closeCallback(player, view, reason)
}