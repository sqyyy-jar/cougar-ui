package com.github.sqyyy.cougar.plugin

import com.github.sqyyy.cougar.plugin.listener.InventoryListener
import net.kyori.adventure.text.Component
import org.bukkit.plugin.java.JavaPlugin

class CougarDatasetPlugin : JavaPlugin() {
    private var name: String? = null
    val dataset = Dataset(hashMapOf())

    override fun onEnable() {
        server.pluginManager.also {
            it.registerEvents(InventoryListener(this), this)
        }
        getCommand("cougar")?.also {
            it.setExecutor { _, _, _, args ->
                if (args.isEmpty()) {
                    this.name = null
                    return@setExecutor true
                }
                this.name = args[0]
                return@setExecutor true
            }
        }
        getCommand("cougarprint")?.also {
            it.setExecutor { sender, _, _, args ->
                if (args.isEmpty()) {
                    sender.sendMessage(Component.text(dataset.toString()))
                    return@setExecutor true
                }
                sender.sendMessage(Component.text(dataset.datasets[args[0]].toString()))
                return@setExecutor true
            }
        }
    }

    fun name(): String {
        return name ?: "no-name"
    }
}