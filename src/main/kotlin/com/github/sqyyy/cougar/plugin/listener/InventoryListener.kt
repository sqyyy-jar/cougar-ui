package com.github.sqyyy.cougar.plugin.listener

import com.destroystokyo.paper.event.inventory.PrepareResultEvent
import com.github.sqyyy.cougar.plugin.CougarDatasetPlugin
import com.github.sqyyy.cougar.plugin.Entry
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*

class InventoryListener(private val cougar: CougarDatasetPlugin) : Listener {
    @EventHandler
    fun on(event: InventoryInteractEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(event, event.eventName)
        dataset += entry
    }

    @EventHandler
    fun on(event: InventoryClickEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(
            event,
            event.eventName,
            event.action,
            event.click,
            event.slotType,
            event.isLeftClick,
            event.isRightClick,
            event.isShiftClick
        )
        dataset += entry
    }

    @EventHandler
    fun on(event: InventoryDragEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(event, event.eventName, dragType = event.type)
        dataset += entry
    }

    @EventHandler
    fun on(event: InventoryCreativeEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(
            event,
            event.eventName,
            event.action,
            event.click,
            event.slotType,
            event.isLeftClick,
            event.isRightClick,
            event.isShiftClick
        )
        dataset += entry
    }

    @EventHandler
    fun on(event: PrepareResultEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(event, event.eventName)
        dataset += entry
    }

    @EventHandler
    fun on(event: InventoryEvent) {
        val dataset = cougar.dataset.datasets.getOrPut(cougar.name()) { mutableListOf() }
        if (dataset.lastOrNull()?.obj == event) {
            return
        }
        val entry = Entry(event, event.eventName)
        dataset += entry
    }
}