package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel

abstract class AbstractSlotPanel(private val slot: Int) : Panel {
    override fun collidesWith(slot: Int): Boolean = slot == this.slot
}