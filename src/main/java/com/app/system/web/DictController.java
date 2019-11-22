package com.app.system.web;

import com.app.app.model.Family;
import com.app.app.service.FamilyService;
import com.app.app.web.FamilyController;
import com.app.sqds.util.*;
import com.app.system.model.Dict;
import com.app.system.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 数据字典管理
 */
@Controller
@RequestMapping(Constant.VIEW_PREFIX + "system/dict")
public class DictController {
    private Logger logger = LoggerFactory.getLogger(DictController.class);

    @Autowired
    private DictService dictService;

    @RequestMapping("list")
    public void list(){
        logger.debug("dict list");
    }

    @RequestMapping("listData")
    @ResponseBody
    public PageData listData(){
        List<Dict> rootDictList = dictService.rootDictList();
        List<Map<String,String>> list = new Vector<>();
        for(Dict dict : rootDictList){
            Map<String, String> row = new HashMap<String, String>();
            row.put("name", StringUtils.getNotNullString(dict.getName()));
            row.put("id", dict.getId()+"");
            List<Dict> sonList = dictService.getList(dict.getId());
            String data = "";
            for(Dict sonDict : sonList){
                data += sonDict.getName()+"("+sonDict.getValue()+")"+", ";
            }
            row.put("value", StringUtils.getNotNullString(data));
            list.add(row);
        }

        PageData pageData = new PageData();
        pageData.setData(list);
        pageData.setCode(0);
        pageData.setCount(list.size());
        return pageData;
    }

    @RequestMapping("form")
    public void form(ModelMap modelMap){
        int id = ParamUtils.getInt("id",0);
        Dict dict = dictService.get(id);
        List<Dict> dictList = new Vector<>();

        if(dict == null){
            dict = new Dict();
        }else{
            dictList = dictService.getList(dict.getId());
        }

        for(int i=0;i<5;i++){
            dictList.add(new Dict());
        }

        modelMap.addAttribute("dict",dict);
        modelMap.addAttribute("dictList",dictList);
    }

    @RequestMapping("save")
    @ResponseBody
    public SqdsResponse save() {
        int id = ParamUtils.getInt("id", 0);
        String name = ParamUtils.getString("name", "");
        int size = ParamUtils.getInt("size", 0);

        Dict dict = dictService.get(id);
        if(dict == null){
            dict = new Dict();
            dict.setValid(1);
        }
        dict.setName(name);
        dict.setValue(name);

        List<Integer> removeIdList = new Vector<>();
        List<Dict> sonDictList = new Vector<>();

        for(int i=0; i<size; i++){
            int sonId = ParamUtils.getInt("sonId_"+i,0);
            String sonName = ParamUtils.getString("sonName_"+i,"");
            String color = ParamUtils.getString("color_"+i, "");
            String value = ParamUtils.getString("value_"+i, "");
            int valid = ParamUtils.getInt("valid_"+i, 2);
            int sort = ParamUtils.getInt("sort_"+i, 0);

            logger.debug("sonName = {}", sonName);
            if(StringUtils.isEmpty(value) && sonId!=0){
                removeIdList.add(sonId);
            }
            if(StringUtils.isNotEmpty(sonName)){
                Dict sonDict = null;
                if(sonId != 0){
                    sonDict = dictService.get(sonId);
                }else{
                    sonDict = new Dict();
                }
                if(sonDict != null){
                    sonDict.setName(sonName);
                    sonDict.setColor(color);
                    sonDict.setValue(value);
                    sonDict.setSort(sort);
                    sonDict.setValid(valid);
                    sonDict.setParentDict(dict);
                    sonDictList.add(sonDict);
                }
            }
        }

        dictService.saveDict(dict, sonDictList, removeIdList);


        return new SqdsResponse().success();
    }

    @RequestMapping("delete")
    @ResponseBody
    public SqdsResponse delete() {
        int id = ParamUtils.getInt("id",0);
        dictService.deleteDict(id);
        return new SqdsResponse().success();
    }

}
