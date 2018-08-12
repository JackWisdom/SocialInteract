package git.jw.mcp.socialboard.gui;

import git.jw.mcp.socialboard.IPlugin;
import git.jw.mcp.socialboard.icon.Theme;
import git.jw.mcp.socialboard.icon.button.New;
import git.jw.mcp.socialboard.icon.button.Next;
import git.jw.mcp.socialboard.icon.button.Previous;
import git.jw.mcp.socialboard.inv.ThemeInvHolder;
import git.jw.mcp.socialboard.until.ButtonType;
import git.jw.mcp.socialboard.until.ThemeData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Index{

    /**
     * PowerNBT用于记录玩家发帖和发帖数 给ICON.Theme用
     *
     */
    private int row=6;
    private int page;
    private int item_end;
    private ThemeData data;
    private String title;
    public HashMap<String,Integer> players;
    public   Index(String title){
        this.page=1;
         this.title=title;
        players=new HashMap<>();
        item_end=(row-1)*9;
        data=new ThemeData();
    }
    public HashMap getPlayers(){
        return players;
    }
    public Inventory createInv(){
    Inventory inv=    Bukkit.createInventory(new ThemeInvHolder(),row*9,title);
    addButtons(inv);
    if(getData().getThemeByPage(page,row-1)==null){
        return inv;
    }
    for(Theme t:getData().getThemeByPage(page,row-1)){
        inv.addItem(t.getItemStack());
    }
    return inv;
    }
    public ThemeData getData(){
        return data;
    }

    public void reflush(int page,Inventory inv){
       ArrayList<Theme> items= data.getThemeByPage(page,row);
       int tmp=0;
       while (tmp<=item_end){
           tmp=tmp+1;
           if(tmp>=items.size()){
               inv.setItem(tmp,null);
               continue;
           }
           inv.setItem(tmp,items.get(tmp-1).getItemStack());
       }
    }
    public void NextPage(Inventory inv){
        page=page+1;
        reflush(page,inv);
    }
    public void PrePage(Inventory inv){
        if(page<=1) {
            page = 1;
            return;
        }
        page=page-1;
        reflush(page,inv);
    }
    private void addButtons(Inventory inv){
        int base=(row-1)*9-1;
        if(getPage()!=1){
            inv.setItem(base+1,new Previous().getItemStack());
        }
        inv.setItem(base+5,new New().getItemStack());
        if(isFull(inv)){
            inv.setItem(base+9,new Next().getItemStack());
        }
    }
    private boolean isFull(Inventory inv){
        int max=(row-1)*9;
        for(int i=0;i<max;i++){
            if(inv.getItem(i)==null||inv.getItem(i).getType()== Material.AIR)
                return false;
        }
        return true;
    }
    private int getPage(){
        return page;
    }
    public int getRows(){
        return row;
    }

    public ButtonType getButtonType(int s,Inventory inv){
        if (!isLastRow(getRow(s))&&inv.getItem(s)!=null&&inv.getItem(s).getType()!=Material.AIR){
            return ButtonType.Enter;
        }
        int solt=(s+1)%9;
        //取余数
        if(isLastRow(getRow(s))){
            if(solt==0&&getPage()!=1)
                return ButtonType.Previous;
            else if (solt==5)
                return ButtonType.New;
            else if (solt==8&&isFull(inv))
                return ButtonType.Next;
        }

        return ButtonType.NONE;
    }
    private boolean isLastRow(int row){
        return this.row==row;
    }
    public int getRow(int solt){
        return solt/9 +1;
    }

}
