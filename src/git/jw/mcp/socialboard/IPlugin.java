package git.jw.mcp.socialboard;

import com.google.gson.Gson;
import git.jw.mcp.socialboard.gui.Index;
import git.jw.mcp.socialboard.icon.Theme;
import git.jw.mcp.socialboard.listener.IListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class IPlugin extends JavaPlugin implements DataCenter{
public static IPlugin bean;
    @Override
    public void onDisable(){
        for(String key:indexs.keySet()){
            File f=new File(getDataFolder(),key);
            if(!f.exists()){
                f.mkdir();
            }
            Index index=indexs.get(key);
            for(Theme t:index.getData().getThemes()){
                File data=new File(f,t.getOwner()+"."+t.getOriginalTitle()+".json");
                if(!data.exists()){
                    try {
                        data.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                //开始保存数据
                Gson gson=new Gson();


                try {
                  String s=  gson.toJson(t);
                  OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(data));
                out.write(s);
                out.flush();
                out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                   /*
                    YamlConfiguration cfg=YamlConfiguration.loadConfiguration(data);
                    cfg.set("owner",t.getOwner());
                    cfg.set("title",t.getOriginalTitle());
                    cfg.set("msg",t.getOriginalLore());
                    cfg.set("commits",t.getCommits());
*/
            }
        }
        for(Player p:Bukkit.getOnlinePlayers()){
            p.closeInventory();
        }
    }
    @Override
    public void onEnable(){
        bean=this;
        saveDefaultConfig();
        File[] f=getDataFolder().listFiles();
        for(File file:f){
            if(!file.isDirectory()){
                continue;
            }
                Index index=new Index(file.getName());
                indexs.put(file.getName(),index);

                for(File theme:file.listFiles()){
                    if(theme.isDirectory()||!theme.getName().endsWith(".json")){
                        continue;
                    }
                    try {
                       Gson gson=new Gson();
                       FileInputStream fr=new FileInputStream(theme);
                       InputStreamReader in=new InputStreamReader(fr);
                       Theme themes=gson.fromJson(in,Theme.class);
                        index.getData().addTheme( themes);
                        try {
                            in.close();
                            fr.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(index.players.get(themes.owner)==null){
                            index.players.put(themes.owner,1);
                        }else {
                            index.players.put(themes.owner,index.players.get(themes.owner)+1);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    //开始读取数据
                    // yaml.getClass().getClassLoader().getParent().loadClass("git.jw.mcp.socialboard.icon.Theme");
                    // Class.forName("git.jw.mcp.socialboard.icon.Theme",false,yaml.getClass().getClassLoader());

                }
        }
        getCommand("pcb").setExecutor(new Cmd());
        Bukkit.getPluginManager().registerEvents(new IListener(),this);
    }

}
