package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.PageInfo;
import com.app.sqds.util.StringUtils;
import com.app.system.model.LoginLog;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 登录日志管理
 */
@Service
public class LoginLogService extends HibernateDao<LoginLog> {
    public PageInfo loginLogList(PageInfo pageInfo) {
        Map<String,Object> data = new HashMap<>();
//        List<Object> data = new Vector<Object>();
        String hql = "from LoginLog l where";
        String username = pageInfo.getPostStringValue("username");
        if(StringUtils.isNotEmpty(username)){
            hql += " username  like ? and ";
            data.put("username","%"+username+"%");
        }
        hql += " 1=1 order by id desc";
        System.out.println(hql);
        return list(pageInfo,hql, data);
    }
}
