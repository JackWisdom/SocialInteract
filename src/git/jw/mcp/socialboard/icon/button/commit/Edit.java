package git.jw.mcp.socialboard.icon.button.commit;

import git.jw.mcp.socialboard.until.Item;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import java.util.ArrayList;

public class Edit extends Item {
    @Override
    public ArrayList<String> getLore() {
        return null;
    }

    @Override
    public String getTite() {
        return "§3§f重新编辑主题";
    }

    @Nonnull
    @Override
    public Material getMaterial() {
        return Material.BOOK_AND_QUILL;
    }
    /**修改正文
     *
     */
}
