package com.app.sqds.freemarker;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * 字典转化
 */
public class DictValueEx implements TemplateMethodModelEx {
    @Override
    public Object exec(List args) throws TemplateModelException {
        if(args.size() != 2){
            throw new TemplateModelException("需要传递两个参数：字典名称，字典值");
        }
        return "字典";
    }
}