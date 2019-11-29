package com.app.system.model;

import com.app.sqds.util.StringUtils;

import javax.persistence.*;

/**
 * 数据字典
 * @author ccj
 */
@Entity
@Table(name="dict")
public class Dict {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String value;
    private String color;
    private Integer sort;
    private Integer valid; //1有效；2无效
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDictId")
    private Dict parentDict;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public Dict getParentDict() {
        return parentDict;
    }

    public void setParentDict(Dict parentDict) {
        this.parentDict = parentDict;
    }

    @Transient
    public String getColorName(){
        if(StringUtils.isEmpty(color)){
            return name;
        }else{
            return "<span style='color:"+color+"'>"+ name +"</span>";
        }
    }
}
