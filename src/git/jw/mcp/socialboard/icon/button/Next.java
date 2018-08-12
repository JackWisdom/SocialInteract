package git.jw.mcp.socialboard.icon.button;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import java.util.ArrayList;

public class Next extends Item {
    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public String getTite() {
        return "§e下一页";
    }

    @Override
    public Material getMaterial() {
        return Material.SPECTRAL_ARROW;
    }
    /**下一页
     *
     */
}
