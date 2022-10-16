package com.github.qumasso.lollipopbetterbees;

import com.github.qumasso.lollipopbetterbees.config.ConfigWorker;
import com.github.qumasso.lollipopbetterbees.events.EventManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private ConfigWorker configWorker;

    private EventManager handler;

    @Override
    public void onEnable() {
        initConfigs();
        configWorker = new ConfigWorker(getDataFolder(), this);
        handler = new EventManager(configWorker);
        getServer().getPluginManager().registerEvents(handler, this);
    }

    public static void main(String[] args) {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&x&f&f&e&e&f&f") + " §");
    }

    private void initConfigs() {
        saveDefaultConfig();
        saveResource(ConfigWorker.HIVE_CONFIG_NAME, true);
        saveResource(ConfigWorker.NEST_CONFIG_NAME, true);
    }

}
