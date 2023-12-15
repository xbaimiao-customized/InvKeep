package com.xbaimiao.invKeep

import com.xbaimiao.easylib.EasyPlugin
import com.xbaimiao.easylib.command.command
import com.xbaimiao.easylib.util.hasLore
import com.xbaimiao.easylib.util.registerListener
import com.xbaimiao.easylib.util.takeItem
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

@Suppress("unused")
class InvKeep : EasyPlugin(), Listener {

    override fun enable() {
        saveDefaultConfig()
        registerListener(this)
        command<CommandSender>("invkeep") {
            description = "重载插件"
            permission = "invkeep.admin"
            exec {
                reloadConfig()
                sender.sendMessage("重载完成")
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    fun d(event: PlayerDeathEvent) {
        val lore = config.getString("inv_keep_item_lore") ?: error("未找到 inv_keep_item_lore 配置")
        val player = event.entity
        if (player.inventory.takeItem(1) { hasLore(lore) }) {
            event.keepLevel = true
            event.keepInventory = true
        }
    }

}