package git.jw.mcp.socialboard.listener;

import git.jw.mcp.socialboard.DataCenter;
import git.jw.mcp.socialboard.IPlugin;
import git.jw.mcp.socialboard.gui.Index;
import git.jw.mcp.socialboard.gui.ThemeGui;
import git.jw.mcp.socialboard.icon.Theme;
import git.jw.mcp.socialboard.inv.CommitInvHolder;
import git.jw.mcp.socialboard.inv.Iholder;
import git.jw.mcp.socialboard.inv.ThemeInvHolder;
import git.jw.mcp.socialboard.until.ButtonType;
import git.jw.mcp.socialboard.until.ThemeData;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class IListener implements Listener,DataCenter {
    @EventHandler()
    public void onInv(InventoryClickEvent event){
        if(event.getInventory().getHolder() instanceof Iholder){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onTheme(InventoryClickEvent event){
        if(!(event.getInventory().getHolder() instanceof CommitInvHolder)){
            return;
        }
        String[] title=event.getInventory().getTitle().replaceAll(" ","").split("\\|");
        String index=title[1];
        String origin=title[0];
        String player=event.getInventory().getItem(0).getItemMeta().getDisplayName();
        String owner=player.substring(4,player.length()-1);
        if(player.contains("置顶")){
            owner=player.substring(12,player.length()-1);
        }

        Theme theme=indexs.get(index).getData().getThemeByOwnerAndTitle(owner,origin);
        ThemeData td=indexs.get(index).getData();
        int slot=event.getSlot();
        if(slot==5&&event.getWhoClicked().isOp()){
            indexs.get(index).getData().stickTheme(theme);
            event.getWhoClicked().sendMessage("§a切换置顶成功");
        return;
        }
        String playername=event.getWhoClicked().getName();
        if(slot==6&&event.getCurrentItem()!=null&&event.getCurrentItem().getType()!= Material.AIR){
            event.getWhoClicked().closeInventory();
            //commit
            mode2.put(playername,0);
            themes.put(playername,theme);
            event.getWhoClicked().sendMessage("§a请在聊天框内输入回复 输入\"cancel\"取消 #换行");
            return;
        }
        if(slot==7&&event.getCurrentItem()!=null&&event.getCurrentItem().getType()!= Material.AIR){
            event.getWhoClicked().closeInventory();
            //edit
            mode2.put(playername,1);
            themes.put(playername,theme);
            event.getWhoClicked().sendMessage("§a请在聊天框内输入新内容 输入\"cancel\"取消 #换行");
            return;
        }
        if(slot==8&&event.getClick()==ClickType.SHIFT_LEFT&&event.getCurrentItem()!=null&&event.getCurrentItem().getType()!= Material.AIR){
            event.getWhoClicked().closeInventory();
            //delete
            td.removeTheme(theme);
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().sendMessage("§c删除成功");
            File f=new File(IPlugin.bean.getDataFolder(),index+File.pathSeparator+owner+"."+origin+".json");
            System.out.println("Detecting File"+f.getPath()+"|"+f.exists());
            if(f.exists()){
                f.delete();
            }
            if(indexs.get(index).players.get(owner)!=null){
                int limit=indexs.get(index).players.get(owner)-1;
                if(limit<=0){
                    limit=0;
                }
               // System.out.println(limit+"|limi");
                indexs.get(index).players.put(owner,limit);
                return;
            }
            return;
        }
        if((slot>=18||slot<=17+18)&&event.getCurrentItem()!=null&&event.getCurrentItem().getType()!= Material.AIR){
            if(event.getClick()==ClickType.RIGHT){
                if(!event.getWhoClicked().isOp()&&!owner.equalsIgnoreCase(event.getWhoClicked().getName())){
                    event.getWhoClicked().sendMessage("§c你不能管理帖子");
                    return;
                }
                String t_n=event.getCurrentItem().getItemMeta().getDisplayName();
                String t_o=t_n.substring(4,t_n.length()-3);
                theme.getCommits().remove(t_o);
                event.getWhoClicked().closeInventory();
                event.getWhoClicked().sendMessage("§a删除回复成功");
            }
        }
    }
    private HashMap<String,Theme> themes=new HashMap<>();
    private HashMap<String,Integer> mode2=new HashMap<>();
    @EventHandler
    public void onCommit(AsyncPlayerChatEvent event){
        if(!mode2.containsKey(event.getPlayer().getName())){
            return;
        }
        event.setCancelled(true);
        if(event.getMessage().equalsIgnoreCase("cancel")){
            remove(event.getPlayer().getName());
            event.getPlayer().sendMessage("§c您已放弃");
            return;
        }
        String name=event.getPlayer().getName();
        Theme theme=themes.get(name);
        String msg=event.getMessage();
        int mode=mode2.get(name);
        if(mode==0){
            //commit
            String rex=".{1,64}";
            if(!msg.matches(rex)){
                event.getPlayer().sendMessage("§c内容仅支持数字 汉字 以及字母 长度在64个字符以内");
                return;
            }

            if(theme.getCommits().keySet().size()>=18){
                event.getPlayer().sendMessage("§c一个帖子仅支持18个回复");
                return;
            }
            event.getPlayer().sendMessage("§a发布成功");
            theme.getCommits().put(name,msg);
            mode2.remove(name);
            return;
        }
        if(mode==1){
            //edit
            String rex=".{1,64}";
            if(!msg.matches(rex)){
                event.getPlayer().sendMessage("§c内容仅支持数字 汉字 以及字母 长度在64个字符以内");
                return;
            }
            theme.setMsg(msg);
            event.getPlayer().sendMessage("§a修改成功");
            mode2.remove(name);
            return;
        }
    }
    @EventHandler()
    public void onIndex(InventoryClickEvent event){
        if(!(event.getInventory().getHolder() instanceof ThemeInvHolder)){
            return;
        }
        Index i=  indexs.get(event.getInventory().getTitle());
        if(event.getSlot()<0||event.getSlot()+1>event.getInventory().getSize()){
            return;
        }
       ButtonType type= i.getButtonType(event.getSlot(),event.getInventory());
        Inventory inv=event.getInventory();
        System.out.println(type);
       if(type==ButtonType.Next)

           i.NextPage(inv);
       else if(type==ButtonType.Previous)
           i.PrePage(inv);
       else if(type==ButtonType.New) {
           event.getWhoClicked().closeInventory();
           event.getWhoClicked().sendMessage("§b请在聊天框中输入标题  输入\"cancel\"取消");
           mode.put(event.getWhoClicked().getName(), 0);
           idx.put(event.getWhoClicked().getName(), inv.getName());
       }else if (type==ButtonType.Enter) {
          event.getWhoClicked().closeInventory();
          String title=event.getCurrentItem().getItemMeta().getLore().get(0).substring(2);
          String name=event.getCurrentItem().getItemMeta().getDisplayName();
           String owner=name.substring(4,name.length()-1);
           if(name.contains("置顶")){
               owner=name.substring(12,name.length()-1);
           }

           Theme theme=indexs.get(inv.getTitle()).getData().getThemeByOwnerAndTitle(owner,title);
           ThemeGui tg=new ThemeGui(theme, (Player) event.getWhoClicked());
           event.getWhoClicked().openInventory(tg.createInv(inv.getName()));
       }


    }
    private HashMap<String,String> idx=new HashMap<>();
    private HashMap<String,Integer> mode=new HashMap<>();
    private HashMap<String,String> title=new HashMap<>();
    private HashMap<String,String> msg=new HashMap<>();
    private void remove(String name){
        mode.remove(name);
        title.remove(name);
        msg.remove(name);
        idx.remove(name);
    }
    @EventHandler
    public void publish(AsyncPlayerChatEvent event){
        if(mode.get(event.getPlayer().getName())==null){
            return;
        }
        event.setCancelled(true);
        if(event.getMessage().equalsIgnoreCase("cancel")){
            remove(event.getPlayer().getName());
            event.getPlayer().sendMessage("§c您已放弃");
            return;
        }
        String name=event.getPlayer().getName();
        int mode=this.mode.get(name);
        if(mode==0){
            String rex=".{1,64}";
            String title=event.getMessage();
            if(!title.matches(rex)){
                event.getPlayer().sendMessage("§c标题仅支持数字 汉字 以及字母 长度在16个字符以内");
                return;
            }
            this.title.put(name,title);
            event.getPlayer().sendMessage("§a您的标题为:"+title+"\n现在请您输入内容 以#作为分隔符");
            this.mode.put(name,1);
            return;
        }
        if(mode==1){
            String rex=".{1,64}";
            String title=event.getMessage();
            if(!title.matches(rex)){
                event.getPlayer().sendMessage("§c内容仅支持数字 汉字 以及字母 长度在64个字符以内");
                return;
            }
            this.msg.put(name,title);
            event.getPlayer().sendMessage("§a您的内容为:"+title+"\n正在为您发布帖子中");
            int limit=IPlugin.bean.getConfig().getInt("players."+event.getPlayer().getName());
            if(limit<=1){
                limit=1;
            }
            boolean isnew=false;
            if(indexs.get(idx.get(name)).players.get(event.getPlayer().getName())==null){
                indexs.get(idx.get(name)).players.put(event.getPlayer().getName(),1);
                isnew=true;
            }else if(indexs.get(idx.get(name)).players.get(event.getPlayer().getName())>=limit&&!event.getPlayer().isOp()){
                event.getPlayer().sendMessage("§c您最多能发 "+limit+"个帖子");
                event.getPlayer().sendMessage("§c您已经有 "+indexs.get(idx.get(name)).players.get(event.getPlayer().getName())+"个帖子");
                remove(event.getPlayer().getName());
                return;
            }
            if(!isnew){
                indexs.get(idx.get(name)).players.put(event.getPlayer().getName(),indexs.get(idx.get(name)).players.get(event.getPlayer().getName())+1);
            }
            event.getPlayer().sendMessage("§a发布成功");
            this.mode.remove(name);
           // System.out.println( indexs.get(idx.get(name)).players.get(event.getPlayer().getName())+"mpw");
            Theme theme=new Theme(name,this.title.get(name),this.msg.get(name));
            indexs.get(idx.get(name)).getData().addTheme(theme);
            remove(event.getPlayer().getName());
            return;
        }
    }
}
