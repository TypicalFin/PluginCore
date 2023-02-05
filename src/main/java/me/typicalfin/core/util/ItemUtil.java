package me.typicalfin.core.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@UtilityClass
public class ItemUtil {

    public ItemStack create(Material material, String displayName) {
        return create(material, 0, displayName, null);
    }

    public ItemStack create(Material material, int amount, String displayName) {
        return create(material, amount, displayName, null);
    }

    public ItemStack create(Material material, int amount, String displayName, List<String> lore, ItemFlag... flags) {
        final ItemStack stack = new ItemStack(material, amount);
        final ItemMeta meta = stack.getItemMeta();

        if (displayName != null && !displayName.isEmpty())
            meta.setDisplayName(displayName);

        if (lore != null && !lore.isEmpty())
            meta.setLore(lore);

        stack.setItemMeta(meta);
        stack.addItemFlags(flags);

        return stack;
    }

    public ItemStack copy(ItemStack original) {
        return new ItemStack(original);
    }

    // TODO: 05/02/2023 builder class with item flags, enchantments, everything 

}
