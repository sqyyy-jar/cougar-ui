package com.github.sqyyy.cougar.plugin

import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.DragType
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryType.SlotType

data class Dataset(val datasets: MutableMap<String, MutableList<Entry>>)

data class Entry(
    val obj: Any,
    val eventName: String,
    val action: InventoryAction? = null,
    val click: ClickType? = null,
    val slotType: SlotType? = null,
    val leftClick: Boolean? = null,
    val rightClick: Boolean? = null,
    val shiftClick: Boolean? = null,
    val dragType: DragType? = null
) {
    override fun toString(): String {
        val string = StringBuilder()
        string.append("|>")
        string.appendLine()
        string.append("  event: ", this.eventName)
        string.appendLine()
        string.append("  action: ", this.action)
        string.appendLine()
        string.append("  click: ", this.click)
        string.appendLine()
        string.append("  slot: ", this.slotType)
        string.appendLine()
        string.append("  left: ", this.leftClick)
        string.appendLine()
        string.append("  right: ", this.rightClick)
        string.appendLine()
        string.append("  shift: ", this.shiftClick)
        string.appendLine()
        string.append("  drag: ", this.dragType)
        string.appendLine()
        return string.toString()
    }
}
