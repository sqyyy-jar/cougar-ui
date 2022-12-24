package com.github.sqyyy.cougar

import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent.Reason
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

interface Ui {
    val type: InventoryType
    val rows: Int
    val slots: Int

    fun click(player: Player, view: InventoryView, slot: Int)

    fun place(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean

    fun placeMany(player: Player, view: InventoryView, items: Map<Int, ItemStack>): Boolean

    fun take(player: Player, view: InventoryView, slot: Int): Boolean

    fun replace(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean

    fun canClick(slot: Int): Boolean

    fun canPlace(slot: Int): Boolean

    fun canTake(slot: Int): Boolean

    fun open(player: Player)

    fun close(player: Player, view: InventoryView, reason: Reason)
}