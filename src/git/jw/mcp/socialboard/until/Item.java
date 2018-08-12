package git.jw.mcp.socialboard.until;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public abstract class Item {
    public abstract ArrayList<String> getLore();
    public abstract String getTite();
    @Nonnull
    public abstract Material getMaterial();
    public ItemStack getItemStack(){
    ItemStack im=new ItemStack(getMaterial());
    ItemMeta meta=im.getItemMeta();
    ArrayList<String> lore=new ArrayList<>();
    meta.setLore(getLore());
    meta.setDisplayName(getTite());
    im.setItemMeta(meta);
    return im;
    }
}
