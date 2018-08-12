package git.jw.mcp.socialboard.icon.button;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import java.util.ArrayList;

public class Previous extends Item {
    /**上一页
     *
     */
    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public String getTite() {
        return "§e上一页";
    }

    @Override
    public Material getMaterial() {
        return Material.ARROW;
    }
}
