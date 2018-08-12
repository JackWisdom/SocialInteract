package git.jw.mcp.socialboard.icon.button;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import java.util.ArrayList;

public class New extends Item {
    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public String getTite() {
        return "§e新建主题";
    }

    @Override
    public Material getMaterial() {
        return Material.FIREWORK;
    }
    /**新建帖子
     *
     */

}
