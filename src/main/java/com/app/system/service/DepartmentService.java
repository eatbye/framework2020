package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.Department;
import com.app.system.model.Menu;
import com.app.system.util.DepartmentTree;
import com.app.system.util.MenuTree;
import com.app.system.util.TreeUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 * @author ccj
 */
@Service
public class DepartmentService extends HibernateDao<Department> {

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteDepartment(String departmentIds) {
        String[] departmentIdArray = departmentIds.split(",");
        for(String departmentId : departmentIdArray){
            delete(new Integer(departmentId));
        }
    }

    public List<DepartmentTree<Department>> findDepartment() {
        String hql = "from Department order by sort,id";
        List<Department> departmentList = list(hql, new HashMap<>());
        List<DepartmentTree<Department>> trees = this.convertDepartments(departmentList);

        return TreeUtil.buildDepartTree(trees);
    }

    private List<DepartmentTree<Department>> convertDepartments(List<Department> departments){
        List<DepartmentTree<Department>> trees = new ArrayList<>();
        departments.forEach(dept -> {
            DepartmentTree<Department> tree = new DepartmentTree<>();
            tree.setId(String.valueOf(dept.getId()));
            if(dept.getParentDepartment()!=null){
                tree.setParentId(String.valueOf(dept.getParentDepartment().getId()));
            }
            tree.setName(dept.getDepartmentName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void saveDepartment(Department department) {
        save(department);
    }
}
