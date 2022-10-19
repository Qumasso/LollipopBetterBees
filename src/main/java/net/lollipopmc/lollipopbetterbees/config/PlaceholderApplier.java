package net.lollipopmc.lollipopbetterbees.config;

import org.bukkit.entity.Bee;

import java.util.List;

public interface PlaceholderApplier {

    List<String> applyPlaceholdersForBee(Bee bee);

    String applyPlaceholdersForBeehiveName(int beesAmount, int honeyLevel);

    List<String> applyPlaceholdersForBeehiveLore(int beesAmount, int honeyLevel);

    List<String> applyPlaceholdersForBeehiveClickMessage(int beesAmount, int honeyLevel);

    String applyPlaceholdersForNestName(int beesAmount, int honeyLevel);

    List<String> applyPlaceholdersForNestLore(int beesAmount, int honeyLevel);

    List<String> applyPlaceholdersForNestClickMessage(int beesAmount, int honeyLevel);

}
