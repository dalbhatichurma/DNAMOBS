package com.dnamobs.utils;

import org.bukkit.ChatColor;

/**
 * Utility class for color code translation
 */
public class ColorUtil {
    
    /**
     * Translate color codes in a string
     * Supports both & and § color codes
     */
    public static String colorize(String text) {
        if (text == null) return "";
        return ChatColor.translateAlternateColorCodes('&', text);
    }
    
    /**
     * Strip all color codes from a string
     */
    public static String stripColor(String text) {
        if (text == null) return "";
        return ChatColor.stripColor(text);
    }
}
