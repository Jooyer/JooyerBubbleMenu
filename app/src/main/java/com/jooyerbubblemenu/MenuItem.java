package com.jooyerbubblemenu;

/**
 * 每一项菜单
 * Created by Jooyer on 2017/2/10
 */
public class MenuItem {

    private int icon;
    private String content;
    public MenuItem() {
    }

    public MenuItem(int icon, String content) {
        this.icon = icon;
        this.content = content;
    }


    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
