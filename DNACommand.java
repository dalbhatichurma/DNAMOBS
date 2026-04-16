package com.dnamobs.commands;

import com.dnamobs.DNAMobs;
import com.dnamobs.models.DNAProfile;
import com.dnamobs.utils.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Main command handler for /dna command
 */
public class DNACommand implements CommandExecutor, TabCompleter {
    
    private final DNAMobs plugin;
    
    public DNACommand(DNAMobs plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        
        String subCommand = args[0].toLowerCase();
        
        switch (subCommand) {
            case "reload":
                return handleReload(sender);
            case "stats":
                return handleStats(sender);
            case "reset":
                return handleReset(sender);
            case "toggle":
                return handleToggle(sender);
            default:
                sendHelp(sender);
                return true;
        }
    }
    
    /**
     * Handle reload command
     */
    private boolean handleReload(CommandSender sender) {
        if (!sender.hasPermission("dnamobs.reload")) {
            sendMessage(sender, "no-permission");
            return true;
        }
        
        plugin.reload();
        sendMessage(sender, "reload-success");
        return true;
    }
    
    /**
     * Handle stats command
     */
    private boolean handleStats(CommandSender sender) {
        if (!sender.hasPermission("dnamobs.view")) {
            sendMessage(sender, "no-permission");
            return true;
        }
        
        Map<EntityType, DNAProfile> profiles = plugin.getDNAManager().getAllProfiles();
        
        sendMessage(sender, "stats-header");
        
        profiles.forEach((type, profile) -> {
            if (profile.getTotalKills() > 0) {
                String message = plugin.getConfig().getString("messages.stats-format", "")
                        .replace("%mob%", type.name())
                        .replace("%tier%", String.valueOf(profile.getCurrentTier()))
                        .replace("%kills%", String.valueOf(profile.getTotalKills()));
                sender.sendMessage(ColorUtil.colorize(message));
            }
        });
        
        return true;
    }
    
    /**
     * Handle reset command
     */
    private boolean handleReset(CommandSender sender) {
        if (!sender.hasPermission("dnamobs.reset")) {
            sendMessage(sender, "no-permission");
            return true;
        }
        
        plugin.getDNAManager().resetAll();
        plugin.getDataStorage().saveData();
        sendMessage(sender, "reset-success");
        return true;
    }
    
    /**
     * Handle toggle command
     */
    private boolean handleToggle(CommandSender sender) {
        if (!sender.hasPermission("dnamobs.toggle")) {
            sendMessage(sender, "no-permission");
            return true;
        }
        
        boolean newState = !plugin.isSystemEnabled();
        plugin.setSystemEnabled(newState);
        
        if (newState) {
            sendMessage(sender, "toggle-enabled");
        } else {
            sendMessage(sender, "toggle-disabled");
        }
        
        return true;
    }
    
    /**
     * Send help message
     */
    private void sendHelp(CommandSender sender) {
        sender.sendMessage(ColorUtil.colorize("&8&m----------&r &bDNAMobs Commands &8&m----------"));
        sender.sendMessage(ColorUtil.colorize("&7/dna reload &8- &eReload configuration"));
        sender.sendMessage(ColorUtil.colorize("&7/dna stats &8- &eView evolution statistics"));
        sender.sendMessage(ColorUtil.colorize("&7/dna reset &8- &eReset all DNA data"));
        sender.sendMessage(ColorUtil.colorize("&7/dna toggle &8- &eToggle evolution system"));
    }
    
    /**
     * Send message from config
     */
    private void sendMessage(CommandSender sender, String key) {
        String prefix = plugin.getConfig().getString("messages.prefix", "");
        String message = plugin.getConfig().getString("messages." + key, "");
        sender.sendMessage(ColorUtil.colorize(prefix + message));
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            List<String> subCommands = Arrays.asList("reload", "stats", "reset", "toggle");
            String input = args[0].toLowerCase();
            
            for (String sub : subCommands) {
                if (sub.startsWith(input)) {
                    completions.add(sub);
                }
            }
        }
        
        return completions;
    }
}
