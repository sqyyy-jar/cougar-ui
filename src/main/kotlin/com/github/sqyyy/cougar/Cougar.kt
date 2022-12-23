package com.github.sqyyy.cougar

import com.github.sqyyy.cougar.internal.SystemListener
import org.bukkit.plugin.Plugin

object Cougar {
    private var initialized = false

    @JvmStatic
    fun initializeSystem(plugin: Plugin) {
        if (initialized) {
            throw IllegalStateException("Cougar is already initialized")
        }
        plugin.server.pluginManager.run {
            registerEvents(SystemListener(), plugin)
        }
        initialized = true
    }
}