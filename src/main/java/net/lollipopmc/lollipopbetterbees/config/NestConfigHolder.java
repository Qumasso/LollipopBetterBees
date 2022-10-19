package net.lollipopmc.lollipopbetterbees.config;

import net.lollipopmc.lib.configurate.objectmapping.ConfigSerializable;
import net.lollipopmc.lib.configuration.holder.ConfigHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@ConfigSerializable
public class NestConfigHolder extends ConfigHolder<NestConfigHolder> {

    public String name = "Улей с ${beesAmount} замечательными пчёлками ^_^";

    public List<String> lore = new ArrayList<>();

    public List<String> clickMessage = new ArrayList<>();

    public NestConfigHolder(File baseFilePath) {
        super(baseFilePath);
        lore.add("Уровень мёда: ${honeyLevel}");
        clickMessage.add("В этом гнезде ${beesAmount} пчёл");
        clickMessage.add("Здесь находится ${honeyLevel} единиц нектара");
    }

    public NestConfigHolder() {
        this(null);
    }

}
