package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.Dict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典管理
 * @author ccj
 */
@Service
public class DictService extends HibernateDao<Dict> {

    public List<Dict> rootDictList() {
        String hql = "from Dict where parentDict is null order by id";
        return list(hql);
    }

    public List<Dict> getList(Integer parentId) {
        String hql = "from Dict where parentDict.id=? order by sort,id";
        return list(hql, parentId);
    }

    public void saveDict(Dict dict, List<Dict> sonDictList, List<Integer> removeIdList) {
        save(dict);
        for(Dict sonDict : sonDictList){
            save(sonDict);
        }
        for(Integer id : removeIdList){
            delete(id);
        }
    }

    public void deleteDict(int id) {
        List<Dict> sonDict = getList(id);
        for(Dict dict : sonDict){
            deleteObject(dict);
        }
        delete(id);
    }
}
