package net.lollipopmc.lollipopbetterbees;

import net.lollipopmc.lollipopbetterbees.config.BeeConfigHolder;
import net.lollipopmc.lollipopbetterbees.config.ConfigWorker;
import net.lollipopmc.lollipopbetterbees.config.HiveConfigHolder;
import net.lollipopmc.lollipopbetterbees.config.NestConfigHolder;
import net.lollipopmc.lollipopbetterbees.events.EventListener;
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

    private void initConfigs() {
        beeConfig = new BeeConfigHolder(new File(getDataFolder(), "bees.yml")).load();
        hiveConfig = new HiveConfigHolder(new File(getDataFolder(), "hive.yml")).load();
        nestConfig = new NestConfigHolder(new File(getDataFolder(), "nest.yml")).load();
    }

}
