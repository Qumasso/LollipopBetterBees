package net.lollipopmc.lollipopbetterbees;

import net.lollipopmc.lollipopbetterbees.config.BeeConfigHolder;
import net.lollipopmc.lollipopbetterbees.config.ConfigWorker;
import net.lollipopmc.lollipopbetterbees.config.HiveConfigHolder;
import net.lollipopmc.lollipopbetterbees.config.NestConfigHolder;
import net.lollipopmc.lollipopbetterbees.events.EventListener;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class BetterBeesPlugin extends JavaPlugin {

    private ConfigWorker configWorker;

    private EventListener handler;

    private HiveConfigHolder hiveConfig;

    private NestConfigHolder nestConfig;

    private BeeConfigHolder beeConfig;

    @Override
    public void onEnable() {
        initConfigs();
        configWorker = new ConfigWorker(hiveConfig, nestConfig, beeConfig);
        handler = new EventListener(configWorker);
        getServer().getPluginManager().registerEvents(handler, this);
    }

/*
    public static void main(String[] args) {
        System.out.println(ChatColor.translateAlternateColorCodes('&', "&x&f&f&e&e&f&f") + " §");
    }


 */

    private void initConfigs() {
        beeConfig = new BeeConfigHolder(new File(getDataFolder() + File.separator + "bees.yml"));
        Bukkit.broadcastMessage(beeConfig.toString());
        beeConfig.load();
        hiveConfig = new HiveConfigHolder(new File(getDataFolder() + File.separator + "hive.yml"));
        hiveConfig.load();
        nestConfig = new NestConfigHolder(new File(getDataFolder() + File.separator + "nest.yml"));
        nestConfig.load();
        //saveDefaultConfig();
        /*
        saveResource(ConfigWorker.HIVE_CONFIG_NAME, true);
        saveResource(ConfigWorker.NEST_CONFIG_NAME, true);

         */
    }

}
