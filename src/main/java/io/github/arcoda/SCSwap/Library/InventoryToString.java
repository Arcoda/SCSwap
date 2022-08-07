//Modified version of https://bukkit.org/threads/serialize-inventory-to-string-remake.429071/
package io.github.arcoda.SCSwap.Library;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public class InventoryToString {

    static String sep = "•";
    static String blockSep = "†";

    public static String invToString (Inventory inventory) {
        String serInv = Math.round(inventory.getSize()/9) + blockSep;
        serInv += "e" + blockSep;
        ItemStack[] items = inventory.getContents();
        for (int i = 0;i<Math.round(inventory.getSize()/9)*9;i++) {
            ItemStack item = items[i];
            if (item!=null) {
                if (item.getType()!=Material.AIR) {
                    serInv += "@w" + sep + i;
                    for (int k = 0;k<Material.values().length;k++)
                        if (Material.values()[k].equals(item.getType()))
                            serInv += "@m" + sep + k;
                    serInv += "@a" + sep + item.getAmount();
                    if (item.getDurability()!=0)
                        serInv += "@d" + sep + item.getDurability();
                    if (item.hasItemMeta()) {
                        if (item.getItemMeta().hasDisplayName())
                            serInv += "@dn" + sep + item.getItemMeta().getDisplayName().replaceAll("§", "&");
                        if (item.getItemMeta().hasLore()) {
                            serInv += "@l" + sep + item.getItemMeta().getLore().get(0).replaceAll("§", "&");
                            for (int k = 1;k<item.getItemMeta().getLore().size();k++) {
                                serInv += sep + item.getItemMeta().getLore().get(k).replaceAll("§", "&");
                            }
                        }
                        if (item.getItemMeta().hasEnchants()) {
                            serInv += "@e" + sep;
                            for (Entry<Enchantment,Integer> ench : item.getEnchantments().entrySet()) {
                                for (int k = 0;k<Enchantment.values().length;k++) {
                                    if (Enchantment.values()[k].equals(ench.getKey())) {
                                        if (Enchantment.values().length-k>1) {
                                            serInv += k + "<>" + ench.getValue() + sep;
                                        }else {
                                            serInv += k + "<>" + ench.getValue();
                                        }
                                    }
                                }
                            }
                        }
                        if (item.getItemMeta() instanceof PotionMeta) {
                            PotionData potion = ((PotionMeta) item.getItemMeta()).getBasePotionData();
                            serInv += "@p" + sep + potion.getType() + sep + (potion.isExtended() ? 1 : 0) + sep + (potion.isUpgraded() ? 1 : 0);
                        }
                    }
                    serInv += blockSep;
                }
            }
        }
        return serInv;
    }

    public static Inventory stringToInv (String string) {
        String[] blocks = string.split(blockSep);
        Inventory ser = Bukkit.createInventory(null, Integer.valueOf(blocks[0])*9, blocks[1].replaceAll("&", "§"));
        for (int j = 2;j<blocks.length;j++) {
            String[] itemAttributes = blocks[j].split("@");
            ItemStack item = null;
            int where = 0;
            for (int i = 0;i<itemAttributes.length;i++) {
                String[] attribute = itemAttributes[i].split(sep);
                if (attribute[0].equals("w")) {
                    where = Integer.valueOf(attribute[1]);
                }else if (attribute[0].equals("m")) {
                    item = new ItemStack(Material.values()[Integer.valueOf(attribute[1])], 1);
                }else if (attribute[0].equals("a")) {
                    item.setAmount(Integer.valueOf(attribute[1]));
                }else if (attribute[0].equals("d")) {
                    item.setDurability(Short.valueOf(attribute[1]));
                }else if (attribute[0].equals("dn")) {
                    ItemMeta itemMeta = item.getItemMeta();
                    itemMeta.setDisplayName(attribute[1].replaceAll("&", "§"));
                    item.setItemMeta(itemMeta);
                }else if (attribute[0].equals("l")) {
                    ItemMeta itemMeta = item.getItemMeta();
                    ArrayList<String> lores = new ArrayList<String>();
                    for (int k = 1;k<attribute.length;k++) {
                        lores.add(attribute[k].replaceAll("&", "§"));
                    }
                    itemMeta.setLore(lores);
                    item.setItemMeta(itemMeta);
                }else if (attribute[0].equals("e")) {
                    ItemMeta itemMeta = item.getItemMeta();
                    for (int k = 1;k<attribute.length;k++) {
                        String[] spe = attribute[k].split("<>");
                        itemMeta.addEnchant(Enchantment.values()[Integer.valueOf(spe[0])], Integer.valueOf(spe[1]), true);
                    }
                    item.setItemMeta(itemMeta);
                }else if (attribute[0].equals("p")) {
                    PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
                    potionMeta.setBasePotionData(new PotionData(PotionType.valueOf(attribute[1]), attribute[2].equals("1"), attribute[3].equals("1")));
                    item.setItemMeta(potionMeta);
                }
            }
            ser.setItem(where, item);
        }
        return ser;
    }
}