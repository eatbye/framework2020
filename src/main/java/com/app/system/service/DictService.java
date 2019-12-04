package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.system.model.Dict;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 字典管理
 * @author ccj
 */
@Service
public class DictService extends HibernateDao<Dict> {

    public List<Dict> rootDictList() {
        String hql = "from Dict where parentDict is null order by id";
        return list(hql, new HashMap<>());
    }

    public List<Dict> getList(Integer parentId) {
        String hql = "from Dict where parentDict.id=:parentId order by sort,id";
        Map<String,Object> values = new HashMap<>();
        values.put("parentId", parentId);
        return list(hql, values);
    }

    /**
     * 根据字典名称获取
     * @param name
     * @return
     */
    public Dict getDictByName(String name) {
        String hql = "from Dict where name=:name";
        Map<String,Object> data = new HashMap<>();
        data.put("name",name);
        return getSingleResult(hql, data);
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

    /**
     * 根据上一级字典，去的下级所有字典
     * @param name
     * @return
     */
    public List<Dict> listByParentName(String name) {
        Dict dict = getDictByName(name);
        if(dict == null){
            return new Vector<Dict>();
        }else{
            List<Dict> dictList = getList(dict.getId());
            return dictList;
        }
    }

}
