package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Ui
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.Range

class PaperUiImpl : Ui {
    override val type: InventoryType
    override val rows: Int
    override val slots: Int
    private val title: Component
    private val panels: Array<MutableList<Panel>?>
    private val clickMap: BooleanArray
    private val placeMap: BooleanArray
    private val takeMap: BooleanArray

    @JvmOverloads
    constructor(rows: @Range(from = 1, to = 6) Int, title: Component, panels: List<List<Panel>> = listOf()) {
        this.type = InventoryType.CHEST
        this.rows = rows
        this.slots = rows * 9
        this.title = title
        this.panels = arrayOfNulls(16)
        panels.forEachIndexed { index, panel -> this.panels.getOrNull(index)?.addAll(panel) }
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

    @JvmOverloads
    constructor(type: InventoryType, title: Component, panels: List<List<Panel>> = listOf()) {
        this.type = type
        when (this.type) {
            InventoryType.CHEST -> {
                this.rows = 3
                this.slots = 3 * 9
            }
            InventoryType.DISPENSER,
            InventoryType.DROPPER,
            -> {
                this.rows = 3
                this.slots = 9
            }
            InventoryType.HOPPER -> {
                this.rows = 1
                this.slots = 5
            }
            else -> throw IllegalArgumentException("Unsupported InventoryType was provided")
        }
        this.title = title
        this.panels = arrayOfNulls(16)
        panels.forEachIndexed { index, panel -> this.panels.getOrNull(index)?.addAll(panel) }
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

    override fun close(player: Player, reason: InventoryCloseEvent.Reason) {
        for (panel in this.panels) {
            panel?.forEach { it.close(player, reason) }
        }
    }

    override fun click(player: Player, view: InventoryView, slot: Int): Boolean {
        var res = false
        for (panel in this.panels) {
            panel?.forEach {
                if (it.collidesWith(slot) && it.canClick(slot)) {
                    res = it.click(player, view, slot)
                }
            }
        }
        return res
    }

    override fun place(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        var res = false
        for (panel in this.panels) {
            panel?.forEach {
                if (it.collidesWith(slot) && it.canPlace(slot)) {
                    res = it.place(player, view, slot, item)
                }
            }
        }
        return res
    }

    override fun placeMany(player: Player, view: InventoryView, items: Map<Int, ItemStack>): Boolean {
        var res = false
        for (panel in this.panels) {
            panel?.forEach {
                for (item in items) {
                    if (it.collidesWith(item.key) && it.canPlace(item.key)) {
                        res = it.placeMany(player, view, items)
                        break;
                    }
                }
            }
        }
        return res
    }

    override fun take(player: Player, view: InventoryView, slot: Int): Boolean {
        var res = false
        for (panel in this.panels) {
            panel?.forEach {
                if (it.collidesWith(slot) && it.canTake(slot)) {
                    res = it.take(player, view, slot)
                }
            }
        }
        return res
    }

    override fun replace(player: Player, view: InventoryView, slot: Int, item: ItemStack): Boolean {
        var res = false
        for (panel in this.panels) {
            panel?.forEach {
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
        TODO("Not yet implemented")
    }
}