package com.github.qumasso.lollipopbetterbees.config;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Bee;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigWorker implements PlaceholderApplier {

    public static final String HIVE_CONFIG_NAME = "hive.yml", NEST_CONFIG_NAME = "nest.yml";

    private Plugin plugin;

    private File hiveConfigFile, nestConfigFile;

    private FileConfiguration hiveConfig, nestConfig;

    private List<String> messageIfHomeExists, messageIfHomeDoesntExist;

    private String hasStungMessage, noStungMessage;

    private String hasNectarMessage, noNectarMessage;

    private String hasFlowerMessage, noFlowerMessage;

    private String hiveItemName, nestItemName;

    private List<String> hiveItemLore, nestItemLore;

    private List<String> hiveClickMessage, nestClickMessage;

    private final Pattern hexPattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public ConfigWorker(File dataFolder, Plugin plugin) {
        this.plugin = plugin;
        hiveConfigFile = new File(dataFolder + File.separator + HIVE_CONFIG_NAME);
        hiveConfig = YamlConfiguration.loadConfiguration(hiveConfigFile);
        nestConfigFile = new File(dataFolder + File.separator + NEST_CONFIG_NAME);
        nestConfig = YamlConfiguration.loadConfiguration(nestConfigFile);
        FileConfiguration config = plugin.getConfig();
        messageIfHomeExists = config.getStringList("message-if-home-exists");
        for (int i = 0; i < messageIfHomeExists.size(); i++) messageIfHomeExists.set(i, applyColors(messageIfHomeExists.get(i)));
        messageIfHomeDoesntExist = config.getStringList("message-if-home-doesnt-exist");
        for (int i = 0; i < messageIfHomeDoesntExist.size(); i++) messageIfHomeDoesntExist.set(i, applyColors(messageIfHomeDoesntExist.get(i)));
        hasStungMessage = config.getString("has-stung-message");
        hasStungMessage = applyColors(hasStungMessage);
        noStungMessage = config.getString("no-stung-message");
        noStungMessage = applyColors(noStungMessage);
        hasNectarMessage = config.getString("has-nectar-message");
        hasNectarMessage = applyColors(hasNectarMessage);
        noNectarMessage = config.getString("no-nectar-message");
        noNectarMessage = applyColors(noNectarMessage);
        hasFlowerMessage = config.getString("has-flower-message");
        hasFlowerMessage = applyColors(hasFlowerMessage);
        noFlowerMessage = config.getString("no-flower-message");
        noFlowerMessage = applyColors(noFlowerMessage);
        hiveItemName = applyColors(hiveConfig.getString("name"));
        nestItemName = applyColors(nestConfig.getString("name"));
        hiveItemLore = hiveConfig.getStringList("lore");
        for (int i = 0; i < hiveItemLore.size(); i++) hiveItemLore.set(i, applyColors(hiveItemLore.get(i)));
        nestItemLore = nestConfig.getStringList("lore");
        for (int i = 0; i < nestItemLore.size(); i++) nestItemLore.set(i, applyColors(nestItemLore.get(i)));
        hiveClickMessage = hiveConfig.getStringList("click-message");
        for (int i = 0; i < hiveClickMessage.size(); i++) hiveClickMessage.set(i, applyColors(hiveClickMessage.get(i)));
        nestClickMessage = nestConfig.getStringList("click-message");
        for (int i = 0; i < nestClickMessage.size(); i++) nestClickMessage.set(i, applyColors(nestClickMessage.get(i)));
    }

    @Override
    public List<String> applyPlaceholdersForBee(Bee bee) {
        List<String> result;
        if (bee.getHive() == null) {
            List<String> target = messageIfHomeDoesntExist;
            result = new ArrayList<>(messageIfHomeDoesntExist.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("\\$\\{stungMessage\\}", bee.hasStung() ? hasStungMessage : noStungMessage);
                colored = colored.replaceAll("\\$\\{nectarMessage\\}", bee.hasNectar() ? hasNectarMessage : noNectarMessage);
                colored = colored.replaceAll("\\$\\{flowerMessage\\}", bee.getFlower() == null ? hasFlowerMessage : noFlowerMessage);
                result.add(colored);
            }
        }
        else {
            List<String> target = messageIfHomeExists;
            result = new ArrayList<>(messageIfHomeExists.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("\\$\\{stungMessage\\}", bee.hasStung() ? hasStungMessage : noStungMessage);
                colored = colored.replaceAll("\\$\\{nectarMessage\\}", bee.hasNectar() ? hasNectarMessage : noNectarMessage);
                colored = colored.replaceAll("\\$\\{flowerMessage\\}", bee.getFlower() == null ? hasFlowerMessage : noFlowerMessage);
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
        return hiveItemName
                .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel));
    }

    @Override
    public List<String> applyPlaceholdersForBeehiveLore(int beesAmount, int honeyLevel) {
        List<String> lore = new ArrayList<>(hiveItemLore.size());
        for (int i = 0; i < hiveItemLore.size(); i++) {
            lore.add(hiveItemLore.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return lore;
    }

    @Override
    public List<String> applyPlaceholdersForBeehiveClickMessage(int beesAmount, int honeyLevel) {
        List<String> message = new ArrayList<>(hiveClickMessage.size());
        for (int i = 0; i < hiveClickMessage.size(); i++) {
            message.add(hiveClickMessage.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return message;
    }

    @Override
    public String applyPlaceholdersForNestName(int beesAmount, int honeyLevel) {
        return nestItemName
                .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel));
    }

    @Override
    public List<String> applyPlaceholdersForNestLore(int beesAmount, int honeyLevel) {
        List<String> lore = new ArrayList<>(nestItemLore.size());
        for (int i = 0; i < nestItemLore.size(); i++) {
            lore.add(nestItemLore.get(i)
                    .replaceAll("\\$\\{beesAmount\\}", String.valueOf(beesAmount))
                    .replaceAll("\\$\\{honeyLevel\\}", String.valueOf(honeyLevel)));
        }
        return lore;
    }

    @Override
    public List<String> applyPlaceholdersForNestClickMessage(int beesAmount, int honeyLevel) {
        List<String> message = new ArrayList<>(nestClickMessage.size());
        for (int i = 0; i < nestClickMessage.size(); i++) {
            message.add(nestClickMessage.get(i)
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
