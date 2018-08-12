package git.jw.mcp.socialboard.icon;

        import git.jw.mcp.socialboard.until.Item;
        import org.bukkit.Material;

        import javax.annotation.Nonnull;
        import java.util.ArrayList;

/**
 * 封面
 * 装饰用
 * */
public class Cover extends Item {
        @Override
        public ArrayList<String> getLore() {
                return null;
        }

        @Override
        public String getTite() {
                return "|";
        }

        @Override
        @Nonnull
        public Material getMaterial() {
                return Material.STAINED_GLASS_PANE;
        }
}
