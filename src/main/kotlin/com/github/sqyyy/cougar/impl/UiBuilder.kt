package com.github.sqyyy.cougar.impl

import com.github.sqyyy.cougar.Panel
import com.github.sqyyy.cougar.Slot
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.Range
import kotlin.math.max
import kotlin.math.min

class PaperUiBuilder {
    var title: Component = Component.empty()
    var type: InventoryType = InventoryType.CHEST
        set(value) {
            rows = when (value) {
                InventoryType.CHEST -> {
                    min(6, max(1, rows))
                }
                InventoryType.DISPENSER,
                InventoryType.DROPPER,
                -> 3
                InventoryType.HOPPER -> 1
                else -> throw IllegalArgumentException("Unsupported InventoryType was provided")
            }
            field = value
        }
    var rows: Int = 3
        set(value) {
            when (type) {
                InventoryType.CHEST -> if (value > 6 || value < 1) {
                    throw IllegalArgumentException("Invalid amount of rows")
                }
                InventoryType.DISPENSER, InventoryType.DROPPER -> if (value != 3) {
                    throw IllegalArgumentException("Invalid amount of rows")
                }
                InventoryType.HOPPER -> if (value != 1) {
                    throw IllegalArgumentException("Invalid amount of rows")
                }
                else -> throw IllegalStateException()
            }
            field = value
        }
    internal var panels: MutableList<MutableList<Panel>> = MutableList(16) { mutableListOf() }

    fun fill(priority: @Range(from = 0, to = 15) Int, from: Slot, to: Slot, fillItem: ItemStack) {
        panels[priority].add(
            when (type) {
                InventoryType.CHEST -> FillPanel(from.chestSlot, to.chestSlot, fillItem, 9)
                InventoryType.DISPENSER,
                InventoryType.DROPPER,
                -> FillPanel(from.dispenserSlot, to.dispenserSlot, fillItem, 3)
                InventoryType.HOPPER -> FillPanel(from.hopperSlot, to.hopperSlot, fillItem, 5)
                else -> throw IllegalArgumentException("Unsupported InventoryType was provided")
            }
        )
    }

    operator fun List<List<Panel>>.unaryPlus() {
        this.forEachIndexed { index, panel ->
            if (panels.size <= index) {
                return@forEachIndexed
            }
            panels[index].addAll(panel)
        }
    }
}

fun paperUi(builder: PaperUiBuilder.() -> Unit): PaperUi {
    val res = PaperUiBuilder()
    builder(res)
    if (res.type == InventoryType.CHEST) {
        return PaperUi(res.title, res.rows, res.panels)
    }
    return PaperUi(res.title, res.type, res.panels)
}