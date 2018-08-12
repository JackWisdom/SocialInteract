package git.jw.mcp.socialboard;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Cmd implements CommandExecutor,DataCenter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)){
            return true;
            }
            Player p= (Player) commandSender;
        if(strings.length!=1){
            return true;
        }
        String id=strings[0];
        if(indexs.containsKey(id)){
            p.openInventory(indexs.get(id).createInv());
        }
        return true;
    }
}
