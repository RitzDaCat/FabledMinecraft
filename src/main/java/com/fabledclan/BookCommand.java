package com.fabledclan;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class BookCommand implements CommandExecutor {
    
    private Main plugin;
    
    public BookCommand(Main plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }
        
        Player player = (Player) sender;
        
        ItemStack fabledBook = null;
        int fabledBookSlot = -1;
    
        // Search for an existing Fabled Book in the player's inventory
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && item.getType() == Material.WRITTEN_BOOK && item.hasItemMeta()) {
                BookMeta bookMeta = (BookMeta) item.getItemMeta();
                if (bookMeta.hasTitle() && bookMeta.getTitle().equals("Welcome to Fabled Clan")) {
                    fabledBook = item;
                    fabledBookSlot = i;
                    break;
                }
            }
        }
    
        // If the Fabled Book is not found, create a new one
        if (fabledBook == null) {
            fabledBook = new ItemStack(Material.WRITTEN_BOOK);
        }
    
        // Update the Fabled Book's content
        BookMeta bookMeta = (BookMeta) fabledBook.getItemMeta();
        bookMeta.setTitle("Welcome to Fabled Clan");
        bookMeta.setAuthor("Fabled Clan");
        List<BaseComponent[]> pages = new ArrayList<>();
        pages.add(new BaseComponent[]{new TextComponent("Welcome to Fabled Clan")});
        pages.add(plugin.getPlayerJoinListener().createSecondPage(player));
        pages.addAll(plugin.getPlayerJoinListener().createEnemyPages());

        bookMeta.spigot().setPages(pages);
        fabledBook.setItemMeta(bookMeta);

        // Add or update the Fabled Book in the player's inventory
        if (fabledBookSlot == -1) {
            player.getInventory().addItem(fabledBook);
        } else {
            player.getInventory().setItem(fabledBookSlot, fabledBook);
        }
        
        player.sendMessage(ChatColor.GREEN + "You have received a new Fabled Book.");
        return true;
    }
}
