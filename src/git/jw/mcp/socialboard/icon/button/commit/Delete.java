package git.jw.mcp.socialboard.icon.button.commit;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Delete extends Item {
    @Override
    public ArrayList<String> getLore() {
        ArrayList arrayList=new ArrayList();
        arrayList.add("§c<SHIFT+左键删除>");
        return arrayList;
    }

    @Override
    public String getTite() {
        return "§e删除帖子";
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.BARRIER;
    }
    /**
     * 删除帖子
     */
}
