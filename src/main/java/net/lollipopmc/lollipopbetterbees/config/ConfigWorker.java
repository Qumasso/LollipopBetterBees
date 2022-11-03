package net.lollipopmc.lollipopbetterbees.config;

import net.kyori.adventure.text.Component;
import net.lollipopmc.lib.configuration.holder.ConfigHolder;
import net.lollipopmc.lib.minimessage.MiniMessage;
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
    }

    @Override
    public List<Component> applyPlaceholdersForBee(Bee bee) {
        List<Component> result;
        if (bee.getHive() == null) {
            List<String> target = beeConfig.messageIfHomeDoesntExist;
            result = new ArrayList<>(beeConfig.messageIfHomeDoesntExist.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("<stung-message>", bee.hasStung() ? beeConfig.hasStungMessage : beeConfig.noStungMessage);
                colored = colored.replaceAll("<nectar-message>", bee.hasNectar() ? beeConfig.hasNectarMessage : beeConfig.noNectarMessage);
                colored = colored.replaceAll("<flower-message>", bee.getFlower() == null ? beeConfig.hasFlowerMessage : beeConfig.noFlowerMessage);
                result.add(MiniMessage.get().deserialize(colored));
            }
        }
        else {
            List<String> target = beeConfig.messageIfHomeExists;
            result = new ArrayList<>(beeConfig.messageIfHomeExists.size());
            for (int i = 0; i < target.size(); i++) {
                String colored = target.get(i);
                colored = colored.replaceAll("<stung-message>", bee.hasStung() ? beeConfig.hasStungMessage : beeConfig.noStungMessage);
                colored = colored.replaceAll("<nectar-message>", bee.hasNectar() ? beeConfig.hasNectarMessage : beeConfig.noNectarMessage);
                colored = colored.replaceAll("<flower-message>", bee.getFlower() == null ? beeConfig.hasFlowerMessage : beeConfig.noFlowerMessage);
                colored = colored.replaceAll("<x>", String.valueOf(bee.getHive().getBlockX()));
                colored = colored.replaceAll("<y>", String.valueOf(bee.getHive().getBlockY()));
                colored = colored.replaceAll("<z>", String.valueOf(bee.getHive().getBlockZ()));
                result.add(MiniMessage.get().deserialize(colored));
            }
        }
        return result;
    }

    @Override
    public Component applyPlaceholdersForBeehiveName(int beesAmount, int honeyLevel) {
        return MiniMessage.get().deserialize(hiveConfig.name
                .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                .replaceAll("<honey-level>", String.valueOf(honeyLevel)));
    }

    @Override
    public List<Component> applyPlaceholdersForBeehiveLore(int beesAmount, int honeyLevel) {
        List<Component> lore = new ArrayList<>(hiveConfig.lore.size());
        for (int i = 0; i < hiveConfig.lore.size(); i++) {
            lore.add(MiniMessage.get().deserialize(hiveConfig.lore.get(i)
                    .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                    .replaceAll("<honey-level>", String.valueOf(honeyLevel))));
        }
        return lore;
    }

    @Override
    public List<Component> applyPlaceholdersForBeehiveClickMessage(int beesAmount, int honeyLevel) {
        List<Component> message = new ArrayList<>(hiveConfig.clickMessage.size());
        for (int i = 0; i < hiveConfig.clickMessage.size(); i++) {
            message.add(MiniMessage.get().deserialize(hiveConfig.clickMessage.get(i)
                    .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                    .replaceAll("<honey-level>", String.valueOf(honeyLevel))));
        }
        return message;
    }

    @Override
    public Component applyPlaceholdersForNestName(int beesAmount, int honeyLevel) {
        return MiniMessage.get().deserialize(nestConfig.name
                .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                .replaceAll("<honey-level>", String.valueOf(honeyLevel)));
    }

    @Override
    public List<Component> applyPlaceholdersForNestLore(int beesAmount, int honeyLevel) {
        List<Component> lore = new ArrayList<>(nestConfig.lore.size());
        for (int i = 0; i < nestConfig.lore.size(); i++) {
            lore.add(MiniMessage.get().deserialize(nestConfig.lore.get(i)
                    .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                    .replaceAll("<honey-level>", String.valueOf(honeyLevel))));
        }
        return lore;
    }

    @Override
    public List<Component> applyPlaceholdersForNestClickMessage(int beesAmount, int honeyLevel) {
        List<Component> message = new ArrayList<>(nestConfig.clickMessage.size());
        for (int i = 0; i < nestConfig.clickMessage.size(); i++) {
            message.add(MiniMessage.get().deserialize(nestConfig.clickMessage.get(i)
                    .replaceAll("<bees-amount>", String.valueOf(beesAmount))
                    .replaceAll("<honey-level>", String.valueOf(honeyLevel))));
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
