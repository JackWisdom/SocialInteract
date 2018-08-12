package git.jw.mcp.socialboard.icon.button.commit;

import com.mysql.fabric.xmlrpc.base.Array;
import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Stick extends Item {
    @Override
    public ArrayList<String> getLore() {
        ArrayList list=new ArrayList();
        list.add("点击切换置顶");

        return list;
    }

    @Override
    public String getTite() {
        return "§C[管理员Only]";
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.TORCH;
    }
    /**置顶
     *
     */
}
