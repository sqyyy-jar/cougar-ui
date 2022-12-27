package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

class OpenEventPanel(private val openCallback: (Player, Inventory) -> Unit) : Panel {
    override fun collidesWith(slot: Int): Boolean = false

    override fun canClick(slot: Int): Boolean = false

    override fun canPlace(slot: Int): Boolean = false

    override fun canTake(slot: Int): Boolean = false

    override fun open(player: Player, inventory: Inventory) = openCallback(player, inventory)
}