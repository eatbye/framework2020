package com.app.system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * 部门实体类
 * @author ccj
 */
@Entity
@Table(name="sys_department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String departmentName;  //部门名称
    private String departmentCode;  //部门编号
    private Integer sort;   //排序号
    private Integer valid; //1有效；2无效
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentDepartmentId")
    @JsonIgnore
    private Department parentDepartment;    //上一级部门

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
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

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    @Transient
    public Integer getParentId(){
        if(parentDepartment!=null){
            return parentDepartment.getId();
        }else{
            return null;
        }
    }
}
