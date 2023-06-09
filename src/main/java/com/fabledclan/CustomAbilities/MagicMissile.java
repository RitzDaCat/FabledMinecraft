package com.fabledclan.CustomAbilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class MagicMissile extends SpellAbility {
    public MagicMissile() {
        super("magic_missile", 1, 25);
    }

    public void cast(Player player) {
        if (failedCastChecks(player))
            return;
        // Get the targeted entity
        RayTraceResult rayTraceResult = player.getWorld().rayTraceEntities(player.getEyeLocation(),
                player.getLocation().getDirection(), 100);
        if (rayTraceResult == null || !(rayTraceResult.getHitEntity() instanceof LivingEntity)) {
            player.sendMessage(ChatColor.RED + "No target found for Magic Missile!");
            return;
        }
        LivingEntity target = (LivingEntity) rayTraceResult.getHitEntity();
    
        // Calculate the direction from player to target
        Vector direction = target.getEyeLocation().subtract(player.getEyeLocation()).toVector().normalize();
    
        // Create and launch fireworks along the direction
        for (int i = 0; i < 20; i++) {
            Location spawnLocation = player.getEyeLocation().add(direction.clone().multiply(i));
            Firework firework = (Firework) player.getWorld().spawnEntity(spawnLocation, EntityType.FIREWORK);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();
            fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).with(Type.BURST).trail(true).build());
            fireworkMeta.setPower(1);
            firework.setFireworkMeta(fireworkMeta);
    
            // Set the firework's velocity to move it towards the target
            firework.setVelocity(direction.clone().multiply(0.1));
    
            // Schedule the firework to explode after 1 second
            Bukkit.getScheduler().runTaskLater(getPlugin(), firework::detonate, 20 * 1);
        }
    }
    
}
