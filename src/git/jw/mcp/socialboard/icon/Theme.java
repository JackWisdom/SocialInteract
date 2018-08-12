package git.jw.mcp.socialboard.icon;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Theme extends Item implements Serializable {
    /**
     * 置顶的和非置顶别放一起
     *  里面有一堆回复
     * 在标题页面只需要看标题
     * 由于实时更新 可能严重性能 后果自负
     */
    public boolean isStick=false;
    public String owner;
    public String title;
    public List<String>  msg;
    public HashMap<String,String> commits;
    public Theme(String owner,String title,String msg){
    this.owner=owner;
    this.title=title;

    ArrayList l=new ArrayList();
    for(String s:msg.split("#")){
        l.add("§f"+s);
    }
        this.msg= l;
    commits=new HashMap<>();
    }
    public boolean isStick(){
        return isStick;
    }
    public void setStick(boolean stick){
        this.isStick=stick;
    }
    public void setMsg(String msg){
        ArrayList l=new ArrayList();
        for(String s:msg.split("#")){
            l.add("§f"+s);
        }
        this.msg= l;
    }
    public Theme(String owner,String title,List<String> msg){
        this.owner=owner;
        this.title=title;
        this.msg= msg;
        commits=new HashMap<>();
    }
    public String getOriginalTitle(){
        return this.title;
    }
    public List<String> getOriginalLore(){
        ArrayList<String> lore=new ArrayList<>();
        lore.add("§e"+getOriginalTitle());//0
        lore.add(" ");//1
        lore.addAll(msg);//2
        return lore;
    }
    public HashMap<String,String> getCommits(){
        return this.commits;
    }
    @Override
    public ArrayList<String> getLore() {
        ArrayList<String> lore=new ArrayList<>();
        lore.add("§f"+getOriginalTitle());//0
        lore.add(" ");//1
        lore.add("§9<点击查看详情>");//2
        return lore;
    }
    public String getOwner(){
        return owner;
    }

    public ItemStack getDetailItem(){
        ItemStack im=new ItemStack(getMaterial());
        ItemMeta meta=im.getItemMeta();
        meta.setLore(getOriginalLore());
        meta.setDisplayName(getTite());
        im.setItemMeta(meta);
        return im;
    }
    @Override
    public String getTite() {
        if (isStick){
            return "§6§l[置顶]§a§l"+getOwner()+"说";
        }
        return "§a§l"+getOwner()+"说";
    }

    @Override
    public Material getMaterial() {
        return Material.PAPER;
    }

}
