package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.PageInfo;
import com.app.sqds.util.StringUtils;
import com.app.system.model.User;
import com.app.system.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 用户管理
 */
@Service
public class UserService extends HibernateDao<User> {
    @Autowired private UserRoleService userRoleService;

    /**
     * 用户搜索
     * @param pageInfo
     * @return
     */
    public PageInfo<User> list(PageInfo<User> pageInfo) {
//        List<Object> dataList = new Vector<>();
        Map<String,Object> data = new HashMap<>();

        String hql = "from User u where ";
        String realName = pageInfo.getPostStringValue("realName");
        if(StringUtils.isNotEmpty(realName)){
            hql += " realName like :realName and ";
//            dataList.add("%" + realName + "%");
            data.put("realName","%" + realName + "%");
        }
        String username = pageInfo.getPostStringValue("username");
        if(StringUtils.isNotEmpty(username)){
            hql += " username like :username and ";
            data.put("username","%" + username + "%");
//            dataList.add("%" + username + "%");
        }
        hql += " 1=1 order by u.id desc";
        return list(pageInfo, hql, data);
    }

    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void saveUser(User user, List<UserRole> userRoleList) {
        save(user);
        userRoleService.deleteByUserId(user.getId());
        for(UserRole userRole : userRoleList){
            userRole.setUser(user);
            userRoleService.save(userRole);
        }
    }

    /**
     * 删除用户
     * @param userId
     */
    @CacheEvict(value = {"role","menu"}, allEntries = true)
    public void deleteUser(int userId) {
        userRoleService.deleteByUserId(userId);
        delete(userId);
    }

    public User findByName(String userName) {
        String hql = "from User u where u.username=:username";
        List<User> userList = getSession().createQuery(hql).setParameter("username",userName).list();
//        return getSingleResult(hql,userName);
        if(userList.size()>0){
            return userList.get(0);
        }else{
            return null;
        }
    }
}
