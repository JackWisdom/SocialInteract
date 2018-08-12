package git.jw.mcp.socialboard.until;

import git.jw.mcp.socialboard.icon.Theme;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class ThemeData {
    private ArrayList<Theme> themes;
    public ArrayList<Theme> getThemes(){
        return themes;
    }
    public ThemeData(){
        themes=new ArrayList<>();
    }
    public void removeTheme(Theme theme){
        themes.remove(theme);
    }
    public void stickTheme(Theme theme){
         theme.setStick(!theme.isStick);
        themes.remove(theme);
        if(theme.isStick){
            themes.add(0,theme);
        }else {
            themes.add(1,theme);
        }
    }
    public void addTheme(Theme theme){
        if(theme==null){
            return;
        }
        if(theme.isStick()){
            themes.add(0,theme);
            return;
        }
        themes.add(theme);
    }

    @Nullable
    public Theme getThemeByOwnerAndTitle(String owner,String title){
        for(Theme t:themes){
            if(t.getOriginalTitle().equals(title)&&owner.equalsIgnoreCase(t.getOwner())){
                return t;
            }
        }
        return null;
    }
    /**
     *
     * @param page 页数
     * @param row  一共有几行
     * @return
     */
    @Nullable
    public ArrayList<Theme> getThemeByPage(int page,int row){
        if(page<=0){
            return null;
        }
        int end=row*9*page;
        int start=row*9*(page-1)+1;

        if(getSize()<start){
            return null;
        }
        int tmp=start;
        ArrayList<Theme> list=new ArrayList<>();
        while (tmp<=getSize()&&tmp<=end){
            list.add(themes.get(tmp-1));

            tmp=tmp+1;
        }
        return list;
        /*
        List<Theme> ts=new ArrayList<>();
        if(getStickSize()>start){
            int tmp=start;
            while (tmp<end){
                tmp=tmp+1;
                if(stheme.size()>=tmp){
                    //先添加置顶的
                    ts.add(stheme.get(tmp));
                }
                ts.add
            }
        }
       */

    }
    public int getSize(){
        return themes.size();
    }
}
