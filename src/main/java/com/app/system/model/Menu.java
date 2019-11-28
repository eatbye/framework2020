package com.app.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单
 * @author ccj
 */
@Entity
@Table(name="menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String menuName;    //菜单名称
    private String url;         //访问地址
    private String permission;  //权限
    private String icon;    //图标
    private Integer type;   //1菜单；2按钮
    private Integer sort;   //排序号
    private Integer valid; //1有效；2无效
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentMenuId")
    @JsonIgnore
    private Menu parentMenu; //父级菜单

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Menu getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    @Transient
    public Integer getParentId(){
        if(parentMenu!=null){
            return parentMenu.getId();
        }else{
            return null;
        }
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", menuName='" + menuName + '\'' +
                ", url='" + url + '\'' +
                ", permission='" + permission + '\'' +
                ", icon='" + icon + '\'' +
                ", type=" + type +
                ", sort=" + sort +
                ", valid=" + valid +
                ", parentMenu=" + parentMenu +
                '}';
    }
}
