package net.lollipopmc.lollipopbetterbees.config;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Bee;

import java.util.List;

public interface PlaceholderApplier {

    List<Component> applyPlaceholdersForBee(Bee bee);

    Component applyPlaceholdersForBeehiveName(int beesAmount, int honeyLevel);

    List<Component> applyPlaceholdersForBeehiveLore(int beesAmount, int honeyLevel);

    List<Component> applyPlaceholdersForBeehiveClickMessage(int beesAmount, int honeyLevel);

    Component applyPlaceholdersForNestName(int beesAmount, int honeyLevel);

    List<Component> applyPlaceholdersForNestLore(int beesAmount, int honeyLevel);

    List<Component> applyPlaceholdersForNestClickMessage(int beesAmount, int honeyLevel);

}
