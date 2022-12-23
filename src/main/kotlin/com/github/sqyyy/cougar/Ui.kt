package com.github.sqyyy.cougar

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent.Reason
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

interface Ui {
    val type: InventoryType
    val rows: Int
    val slots: Int
    val title: Component

    fun close(player: Player, reason: Reason)

    fun click(slot: Int) // TODO

    fun place(slot: Int) // TODO

    fun placeMany(slots: Set<Int>) // TODO

    fun take(slot: Int) // TODO

    fun replace(slot: Int) // TODO

    fun canClick(slot: Int): Boolean

    fun canPlace(slot: Int): Boolean

    fun canTake(slot: Int): Boolean

    fun nextPlaceableSlot(inventory: Inventory, itemStack: ItemStack): Int
}