package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Ui
import com.github.sqyyy.cougar.internal.UiHolder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.Range

class PaperUi : Ui {
    override val type: InventoryType
    override val rows: Int
    override val slots: Int
    private val title: Component
    private val panels: Array<MutableList<Panel>?>
    private val clickMap: BooleanArray
    private val placeMap: BooleanArray
    private val takeMap: BooleanArray

    @JvmOverloads
    constructor(title: Component, rows: @Range(from = 1, to = 6) Int, panels: List<List<Panel>> = listOf()) : this(
        title, InventoryType.CHEST, rows, rows * 9, panels
    )

    @JvmOverloads
    constructor(title: Component, type: InventoryType, panels: List<List<Panel>> = listOf()) : this(
        title, type, when (type) {
            InventoryType.CHEST,
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            -> 3
            InventoryType.HOPPER -> 1
            else -> throw IllegalArgumentException("Unsupported InventoryType was provided")
        }, when (type) {
            InventoryType.CHEST -> 3 * 9
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            -> 9
            InventoryType.HOPPER -> 5
            else -> throw IllegalArgumentException("Unsupported InventoryType was provided")
        }, panels
    )

    private constructor(title: Component, type: InventoryType, rows: Int, slots: Int, panels: List<List<Panel>>) {
        this.title = title
        this.type = type
        this.rows = rows
        this.slots = slots
        this.panels = arrayOfNulls(16)
        panels.forEachIndexed { index, panel ->
            if (this.panels.size <= index) {
                return@forEachIndexed
            }
            if (this.panels[index] == null) {
                this.panels[index] = mutableListOf()
            }
            this.panels[index]?.addAll(panel)
        }
        this.clickMap = BooleanArray(this.slots) { false }
        this.placeMap = BooleanArray(this.slots) { false }
        this.takeMap = BooleanArray(this.slots) { false }
        for (slot in 0 until this.slots) {
            for (panelList in this.panels) {
                panelList?.forEach {
                    if (it.collidesWith(slot)) {
                        this.clickMap[slot] = this.clickMap[slot] || it.canClick(slot)
                        this.placeMap[slot] = it.canPlace(slot)
                        this.takeMap[slot] = it.canTake(slot)
                    }
                }
            }
        }
    }

    override fun click(player: Player, view: InventoryView, slot: Int) {
        for (panelList in this.panels) {
            panelList?.forEach {
                if (it.collidesWith(slot) && it.canClick(slot)) {
                    it.click(player, view, slot)
                }
            }
        }
    }

    override fun place(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        var res = false
        for (panelList in this.panels) {
            panelList?.forEach {
                if (it.collidesWith(slot) && it.canPlace(slot)) {
                    res = it.place(player, view, slot, item)
                }
            }
        }
        return res
    }

    override fun placeMany(player: Player, view: InventoryView, items: Map<Int, ItemStack>): Boolean {
        var res = false
        for (panelList in this.panels) {
            panelList?.forEach {
                for (item in items) {
                    if (it.collidesWith(item.key) && it.canPlace(item.key)) {
                        res = it.placeMany(player, view, items)
                        break
                    }
                }
            }
        }
        return res
    }

    override fun take(player: Player, view: InventoryView, slot: Int): Boolean {
        var res = false
        for (panelList in this.panels) {
            panelList?.forEach {
                if (it.collidesWith(slot) && it.canTake(slot)) {
                    res = it.take(player, view, slot)
                }
            }
        }
        return res
    }

    override fun replace(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        var res = false
        for (panelList in this.panels) {
            panelList?.forEach {
                if (it.collidesWith(slot) && it.canPlace(slot) && it.canTake(slot)) {
                    res = it.replace(player, view, slot, item)
                }
            }
        }
        return res
    }

    override fun canClick(slot: Int): Boolean = this.clickMap[slot]

    override fun canPlace(slot: Int): Boolean = this.placeMap[slot]

    override fun canTake(slot: Int): Boolean = this.takeMap[slot]

    override fun open(player: Player) {
        val inventory: Inventory
        val holder = UiHolder(this)
        inventory = if (type == InventoryType.CHEST) {
            Bukkit.createInventory(holder, slots, title)
        } else {
            Bukkit.createInventory(holder, type, title)
        }
        holder.inventory = inventory
        for (panelList in panels) {
            panelList?.forEach { it.open(player, inventory) }
        }
        player.openInventory(inventory)
    }

    override fun close(player: Player, view: InventoryView, reason: InventoryCloseEvent.Reason) {
        for (panelList in this.panels) {
            panelList?.forEach { it.close(player, view, reason) }
        }
    }
}