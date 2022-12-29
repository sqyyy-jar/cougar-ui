package com.github.sqyyy.cougar.impl.panel

import com.github.sqyyy.cougar.Panel

abstract class AbstractSlotPanel(protected val slot: Int) : Panel {
    override fun collidesWith(slot: Int): Boolean = slot == this.slot
}