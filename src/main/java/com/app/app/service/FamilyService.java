package com.app.app.service;

import com.app.app.model.Family;
import com.app.sqds.hibernate.HibernateDao;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
public class FamilyService extends HibernateDao<Family> {
    private Logger logger = LoggerFactory.getLogger(FamilyService.class);

    public List<Family> familyList(){
        List<Family> familyList = getSession().createQuery("from Family").list();
        return familyList;
    }
}
