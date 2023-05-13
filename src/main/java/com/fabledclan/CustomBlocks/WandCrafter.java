package com.fabledclan.CustomBlocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.fabledclan.Main;

import org.bukkit.NamespacedKey;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class WandCrafter extends CustomContainer {

    public WandCrafter() {
        super("wand_crafter", (ChatColor.BOLD + "" + ChatColor.DARK_PURPLE + "Wand Crafter"),
        Material.SMITHING_TABLE);
    }

    public Recipe recipe() {
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(), getName()), getItem());
        recipe.shape(" D ", "DCD", " D ");
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('C', Material.CRAFTING_TABLE);

        return recipe;
    }

    public Inventory makeInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9);
        return inventory;
    }

    public void placeEvent(BlockPlaceEvent event) {
        defaultPlace(event);
    }

    public void breakEvent(BlockBreakEvent event) {
        defaultBreak(event);
    }

    public void interactEvent(PlayerInteractEvent event) {
        event.setCancelled(true);
        event.getPlayer().openInventory(getInventory());
    }
    
}
