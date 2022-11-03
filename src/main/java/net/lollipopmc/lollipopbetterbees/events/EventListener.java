package net.lollipopmc.lollipopbetterbees.events;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.lollipopmc.lollipopbetterbees.config.PlaceholderApplier;
import org.bukkit.Material;
import org.bukkit.block.data.type.Beehive;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Item;
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

public class EventListener implements Listener {

    private PlaceholderApplier applier;

    public EventListener(PlaceholderApplier applier) {
        this.applier = applier;
    }

    @EventHandler
    private void onEntityInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Bee bee)) return;
        if (event.getHand() != EquipmentSlot.OFF_HAND) return;
        List<Component> message = applier.applyPlaceholdersForBee(bee);
        for (Component line : message) {
            event.getPlayer().sendMessage(line);
        }
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.OFF_HAND) return;
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if (e.getClickedBlock().getState() instanceof org.bukkit.block.Beehive hive) {
            int beesAmount = hive.getEntityCount();
            if (e.getClickedBlock().getBlockData() instanceof org.bukkit.block.data.type.Beehive data) {
                int honeyLevel = data.getHoneyLevel();
                if (e.getClickedBlock().getType() == Material.BEEHIVE) {
                    List<Component> message = applier.applyPlaceholdersForBeehiveClickMessage(beesAmount, honeyLevel);
                    message.forEach(s -> e.getPlayer().sendMessage(s));
                }
                else if (e.getClickedBlock().getType() == Material.BEE_NEST) {
                    List<Component> message = applier.applyPlaceholdersForNestClickMessage(beesAmount, honeyLevel);
                    message.forEach(s -> e.getPlayer().sendMessage(s));
                }
            }
        }
    }

    @EventHandler
    private void onEntityPickup(EntityPickupItemEvent e) {
        Item item = e.getItem();
        ItemStack stack = item.getItemStack();
        if (stack.getType() == Material.BEEHIVE) item.setItemStack(renameBeehive(stack));
        else if (stack.getType() == Material.BEE_NEST) item.setItemStack(renameBeeNest(stack));
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent e) {
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
                meta.displayName(applier.applyPlaceholdersForBeehiveName(beesAmount, honeyLevel));
                meta.lore(applier.applyPlaceholdersForBeehiveLore(beesAmount, honeyLevel));
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
                meta.displayName(applier.applyPlaceholdersForNestName(beesAmount, honeyLevel));
                meta.lore(applier.applyPlaceholdersForNestLore(beesAmount, honeyLevel));
                stack.setItemMeta(meta);
            }
        }
        return stack;
    }

}
