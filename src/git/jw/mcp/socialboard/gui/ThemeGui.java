package git.jw.mcp.socialboard.gui;

import git.jw.mcp.socialboard.icon.Cover;
import git.jw.mcp.socialboard.icon.Theme;
import git.jw.mcp.socialboard.icon.button.commit.Delete;
import git.jw.mcp.socialboard.icon.button.commit.Edit;
import git.jw.mcp.socialboard.icon.button.commit.LeaveCommit;
import git.jw.mcp.socialboard.icon.button.commit.Stick;
import git.jw.mcp.socialboard.inv.CommitInvHolder;
import git.jw.mcp.socialboard.inv.ThemeInvHolder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ThemeGui {
    /**
     *主题内容GUI
     */
    private Theme theme;
    private  int row=4;
    private Player opener;
    public ThemeGui(Theme theme,Player opener){
    this.theme=theme;
    this.opener=opener;
    }
    public Inventory createInv(String name){
        Inventory inv= Bukkit.createInventory(new CommitInvHolder(),row*9,theme.getOriginalTitle()+"  |  "+name);
        addButtons(inv);
        addCommits(inv);
        return inv;
    }
    private void addCommits(Inventory inv){
        int start=18;
        for(String key:theme.getCommits().keySet()){
            String msg=theme.getCommits().get(key);
            ItemStack im=new ItemStack(Material.TORCH);
            ItemMeta meta=im.getItemMeta();
            meta.setDisplayName("§e§l"+key+"回复:");
            ArrayList l=new ArrayList();
            for(String s:msg.split("#")){
                l.add("§f"+s);
            }
            l.add(" ");
            l.add("§c<右键删除本条回复>");
            meta.setLore(l);
            im.setItemMeta(meta);
            inv.setItem(start,im);
            start=start+1;
        }

    }
    private void addButtons(Inventory inv){
        inv.setItem(0,theme.getDetailItem());
        if(opener.isOp()){
            inv.setItem(5,new Stick().getItemStack());
        }
        inv.setItem(6,new LeaveCommit().getItemStack());
        if(opener.isOp()||opener.getName().equalsIgnoreCase(theme.getOwner())) {
            inv.setItem(7, new Edit().getItemStack());
            inv.setItem(8,new Delete().getItemStack());
        }
        int s=9;
        int e=17;
        while (s<=e){
            inv.setItem(s,new Cover().getItemStack());
            s=s+1;
        }

    }
}
