package com.app.app.service;

import com.app.app.model.Family;
import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.PageInfo;
import com.app.sqds.util.StringUtils;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

@Service
public class FamilyService extends HibernateDao<Family> {
    private Logger logger = LoggerFactory.getLogger(FamilyService.class);

    @Cacheable(value = "familyList")
    public List<Family> familyList(){
        List<Family> familyList = getSession().createQuery("from Family").list();
        return familyList;
    }

    @CacheEvict(value = "familyList", allEntries = true)
    public void saveFamily(Family family) {
        getSession().save(family);
    }

    public PageInfo<Family> familyList(PageInfo<Family> pageInfo){
        List<Object> dataList = new Vector<>();

        String hql = "from Family where";
        String name = pageInfo.getPostStringValue("name");
        if(StringUtils.isNotEmpty(name)){
            hql += " name like ? and ";
            dataList.add("%"+name+"%");
        }
        String address = pageInfo.getPostStringValue("address");
        if(StringUtils.isNotEmpty(address)){
            hql += " address like ? and ";
            dataList.add("%"+address+"%");
        }
        hql += " 1=1 order by id desc";
        pageInfo = list(pageInfo, hql, dataList.toArray());
        return pageInfo;
    }
}
