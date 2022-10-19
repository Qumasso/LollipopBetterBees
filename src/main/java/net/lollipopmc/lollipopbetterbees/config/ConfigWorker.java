package net.lollipopmc.lollipopbetterbees.config;

import net.lollipopmc.lib.configuration.holder.ConfigHolder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bee;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigWorker implements PlaceholderApplier {

    private FileConfiguration config;

    private HiveConfigHolder hiveConfig;

    private NestConfigHolder nestConfig;

    private BeeConfigHolder beeConfig;

    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public ConfigWorker(HiveConfigHolder hiveConfig, NestConfigHolder nestConfig, BeeConfigHolder beeConfig) {
        this.beeConfig = beeConfig;
        this.hiveConfig = hiveConfig;
        this.nestConfig = nestConfig;
        this.config = config;
        for (int i = 0; i < hiveConfig.clickMessage.size(); i++) {
            hiveConfig.clickMessage.set(i, applyColors(hiveConfig.clickMessage.get(i)));
        }
        for (int i = 0; i < nestConfig.clickMessage.size(); i++) {
            nestConfig.clickMessage.set(i, applyColors(nestConfig.clickMessage.get(i)));
        }
        for (int i = 0; i < hiveConfig.lore.size(); i++) {
            hiveConfig.lore.set(i, applyColors(hiveConfig.lore.get(i)));
        }
        for (int i = 0; i < nestConfig.lore.size(); i++) {
            nestConfig.lore.set(i, applyColors(nestConfig.lore.get(i)));
        }
        for (int i = 0; i < beeConfig.messageIfHomeExists.size(); i++) {
            beeConfig.messageIfHomeExists.set(i, applyColors(beeConfig.messageIfHomeExists.get(i)));
        }
        for (int i = 0; i < beeConfig.messageIfHomeDoesntExist.size(); i++) {
            beeConfig.messageIfHomeDoesntExist.set(i, applyColors(beeConfig.messageIfHomeDoesntExist.get(i)));
        }
        beeConfig.hasNectarMessage = applyColors(beeConfig.hasNectarMessage);
        beeConfig.noNectarMessage = applyColors(beeConfig.noNectarMessage);
        beeConfig.hasFlowerMessage = applyColors(beeConfig.hasFlowerMessage);
        beeConfig.noFlowerMessage = applyColors(beeConfig.noFlowerMessage);
        beeConfig.hasStungMessage = applyColors(beeConfig.hasStungMessage);
        beeConfig.noStungMessage = applyColors(beeConfig.noStungMessage);
        hiveConfig.name = applyColors(hiveConfig.name);
        nestConfig.name = applyColors(nestConfig.name);
    }

    @Override
    public List<String> applyPlaceholdersForBee(Bee bee) {
        List<String> result;
        if (bee.getHive() == null) {
            List<String> target = beeConfig.messageIfHomeDoesntExist;
            result = new ArrayList<>(beeConfig.messageIfHomeDoesntExist.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("\\$\\{stungMessage\\}", bee.hasStung() ? beeConfig.hasStungMessage : beeConfig.noStungMessage);
                colored = colored.replaceAll("\\$\\{nectarMessage\\}", bee.hasNectar() ? beeConfig.hasNectarMessage : beeConfig.noNectarMessage);
                colored = colored.replaceAll("\\$\\{flowerMessage\\}", bee.getFlower() == null ? beeConfig.hasFlowerMessage : beeConfig.noFlowerMessage);
                result.add(colored);
            }
        }
        else {
            List<String> target = beeConfig.messageIfHomeExists;
            result = new ArrayList<>(beeConfig.messageIfHomeExists.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("\\$\\{stungMessage\\}", bee.hasStung() ? beeConfig.hasStungMessage : beeConfig.noStungMessage);
                colored = colored.replaceAll("\\$\\{nectarMessage\\}", bee.hasNectar() ? beeConfig.hasNectarMessage : beeConfig.noNectarMessage);
                colored = colored.replaceAll("\\$\\{flowerMessage\\}", bee.getFlower() == null ? beeConfig.hasFlowerMessage : beeConfig.noFlowerMessage);
                colored = colored.replaceAll("\\$\\{x\\}", String.valueOf(bee.getHive().getBlockX()));
                colored = colored.replaceAll("\\$\\{y\\}", String.valueOf(bee.getHive().getBlockY()));
                colored = colored.replaceAll("\\$\\{z\\}", String.valueOf(bee.getHive().getBlockZ()));
                result.add(colored);
            }
        }
        return result;
    }

    @Override
    public String applyPlaceholdersForBeehiveName(int beesAmount, int honeyLevel) {
        return hiveConfig.name
                .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel));
    }

    @Override
    public List<String> applyPlaceholdersForBeehiveLore(int beesAmount, int honeyLevel) {
        List<String> lore = new ArrayList<>(hiveConfig.lore.size());
        for (int i = 0; i < hiveConfig.lore.size(); i++) {
            lore.add(hiveConfig.lore.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return lore;
    }

    @Override
    public List<String> applyPlaceholdersForBeehiveClickMessage(int beesAmount, int honeyLevel) {
        List<String> message = new ArrayList<>(hiveConfig.clickMessage.size());
        for (int i = 0; i < hiveConfig.clickMessage.size(); i++) {
            message.add(hiveConfig.clickMessage.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return message;
    }

    @Override
    public String applyPlaceholdersForNestName(int beesAmount, int honeyLevel) {
        return nestConfig.name
                .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel));
    }

    @Override
    public List<String> applyPlaceholdersForNestLore(int beesAmount, int honeyLevel) {
        List<String> lore = new ArrayList<>(nestConfig.lore.size());
        for (int i = 0; i < nestConfig.lore.size(); i++) {
            lore.add(nestConfig.lore.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return lore;
    }

    @Override
    public List<String> applyPlaceholdersForNestClickMessage(int beesAmount, int honeyLevel) {
        List<String> message = new ArrayList<>(nestConfig.clickMessage.size());
        for (int i = 0; i < nestConfig.clickMessage.size(); i++) {
            message.add(nestConfig.clickMessage.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return message;
    }

    private String applyColors(String raw) {
        Matcher matcher = hexPattern.matcher(raw);
        while (matcher.find()) {
            ChatColor color = ChatColor.of(raw.substring(matcher.start(), matcher.end()));
            raw = raw.substring(0, matcher.start()) + color + raw.substring(matcher.end());
            matcher = hexPattern.matcher(raw);
        }
        return raw;
    }

}
