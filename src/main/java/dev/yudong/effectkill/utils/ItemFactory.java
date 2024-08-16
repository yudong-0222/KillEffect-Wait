package dev.yudong.effectkill.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Skull;

public class ItemFactory{
    public static ItemStack create(Material material, byte data, String displayName, String... lore) {
        @SuppressWarnings("deprecation")
		ItemStack itemStack = new MaterialData(material, data).toItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        if (lore != null) {
            List<String> finalLore = new ArrayList<>(Arrays.asList(lore));
            itemMeta.setLore(finalLore);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static ItemStack create(Material material, byte data, String displayName) {
        return create(material, data, displayName, (String[])null);
    }
}
