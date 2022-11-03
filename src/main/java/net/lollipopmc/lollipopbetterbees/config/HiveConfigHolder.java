package net.lollipopmc.lollipopbetterbees.config;

import net.lollipopmc.lib.configurate.objectmapping.ConfigSerializable;
import net.lollipopmc.lib.configuration.holder.ConfigHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class HiveConfigHolder extends ConfigHolder<HiveConfigHolder> {

    public String name = "Улей с <bees-amount> замечательными пчёлками ^_^";

    public List<String> lore = new ArrayList<>();

    public List<String> clickMessage = new ArrayList<>();

    public HiveConfigHolder(File baseFilePath) {
        super(baseFilePath);
        lore.add("Уровень мёда: <honey-level>");
        clickMessage.add("В этом улье <bees-amount> пчёл");
        clickMessage.add("Здесь находится <honey-level> единиц нектара");
    }

    public HiveConfigHolder() {
        this(null);
    }

}
