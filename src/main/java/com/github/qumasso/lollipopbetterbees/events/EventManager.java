package com.github.qumasso.lollipopbetterbees.events;

import com.github.qumasso.lollipopbetterbees.config.PlaceholderApplier;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockDataMeta;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.List;

public class EventManager implements Listener {

    private PlaceholderApplier applier;

    public EventManager(PlaceholderApplier applier) {
        this.applier = applier;
    }

    @EventHandler
    public void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Bee)) return;
        if (event.getHand() != EquipmentSlot.OFF_HAND) return;
        Bee bee = (Bee) event.getRightClicked();
        List<String> message = applier.applyPlaceholdersForBee(bee);
        for (String line : message) event.getPlayer().sendMessage(line);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getState() instanceof org.bukkit.block.Beehive hive) {
            int beesAmount = hive.getEntityCount();
            if (e.getClickedBlock().getBlockData() instanceof org.bukkit.block.data.type.Beehive data) {
                int honeyLevel = data.getHoneyLevel();
                if (e.getClickedBlock().getType() == Material.BEEHIVE) {
                    List<String> message = applier.applyPlaceholdersForBeehiveClickMessage(beesAmount, honeyLevel);
                    message.forEach(s -> e.getPlayer().sendMessage(s));
                }
                else if (e.getClickedBlock().getType() == Material.BEE_NEST) {
                    List<String> message = applier.applyPlaceholdersForNestClickMessage(beesAmount, honeyLevel);
                    message.forEach(s -> e.getPlayer().sendMessage(s));
                }
            }
        }
    }

    @EventHandler
    public void onEntityPickup(EntityPickupItemEvent e) {
        Item item = e.getItem();
        ItemStack stack = item.getItemStack();
        if (stack.getType() == Material.BEEHIVE) item.setItemStack(renameBeehive(stack));
        else if (stack.getType() == Material.BEE_NEST) item.setItemStack(renameBeeNest(stack));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClick() == ClickType.CREATIVE) return;
        if (e.getCurrentItem().getType() == Material.BEEHIVE) {
            e.setCurrentItem(renameBeehive(e.getCurrentItem()));
        }
        else if (e.getCurrentItem().getType() == Material.BEE_NEST) {
            e.setCurrentItem(renameBeeNest(e.getCurrentItem()));
        }
    }

    private ItemStack renameBeehive(ItemStack stack) {
        if (stack.getType() == Material.BEEHIVE) {
            int honeyLevel = 0, beesAmount = 0;
            if (stack.getItemMeta() instanceof BlockDataMeta meta) {
                if (meta.hasBlockData() && meta.getBlockData(stack.getType()) instanceof Beehive data) {
                    honeyLevel = data.getHoneyLevel();
                    if (stack.getItemMeta() instanceof BlockStateMeta stateMeta) {
                        if (stateMeta.getBlockState() instanceof org.bukkit.block.Beehive state) {
                            beesAmount = state.getEntityCount();
                        }
                    }
                }
                meta.setDisplayName(applier.applyPlaceholdersForBeehiveName(beesAmount, honeyLevel));
                meta.setLore(applier.applyPlaceholdersForBeehiveLore(beesAmount, honeyLevel));
                stack.setItemMeta(meta);
            }
        }
        return stack;
    }

    private ItemStack renameBeeNest(ItemStack stack) {
        if (stack.getType() == Material.BEE_NEST) {
            int honeyLevel = 0, beesAmount = 0;
            if (stack.getItemMeta() instanceof BlockDataMeta meta) {
                if (meta.hasBlockData() && meta.getBlockData(stack.getType()) instanceof Beehive data) {
                    honeyLevel = data.getHoneyLevel();
                    if (stack.getItemMeta() instanceof BlockStateMeta stateMeta) {
                        if (stateMeta.getBlockState() instanceof org.bukkit.block.Beehive state) {
                            beesAmount = state.getEntityCount();
                        }
                    }
                }
                meta.setDisplayName(applier.applyPlaceholdersForNestName(beesAmount, honeyLevel));
                meta.setLore(applier.applyPlaceholdersForNestLore(beesAmount, honeyLevel));
                stack.setItemMeta(meta);
            }
        }
        return stack;
    }

}
