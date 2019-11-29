package com.app.system.tag;

import com.app.sqds.util.SpringContextUtil;
import com.app.sqds.util.StringUtils;
import com.app.system.model.Dict;
import com.app.system.service.DictService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

/**
 * 数据字典freemarker tag
 * @author ccj
 */
public class DictTag implements TemplateDirectiveModel {
    private Logger logger = LoggerFactory.getLogger(DictTag.class);

    @Override
    public void execute(Environment environment, Map map, TemplateModel[] templateModels, TemplateDirectiveBody templateDirectiveBody) throws TemplateException, IOException {
        DictService dictService = (DictService) SpringContextUtil.getBean("dictService");
        Writer writer = environment.getOut();

        String name = getParam(map,"name");     //表单名字
        String dictName = getParam(map,"dictName"); //数据字典名字
        String type = getParam(map,"type"); //类型 radio selected view 或者空
        String value = getParam(map,"value");   //值
        String firstValue = getParam(map,"firstValue"); //如果为selected类型，第一个显示的内容

        List<Dict> dictList = dictService.listByParentName(dictName);

        //查看数据字典的值
        if(StringUtils.isEmpty(type) || type.equals("view")){
            for(Dict dict : dictList){
                logger.debug("dict value = {}", dict.getValue() );
                if(dict.getValue().equals(value)){
                    writer.append(dict.getColorName());
                }
            }
        }else if(type.equals("selected")){
            //下拉框
            writer.append("<select name='"+name+"'>");
            if(StringUtils.isNotEmpty(firstValue)){
                writer.append("<option value=''>"+ firstValue+"</option>");
            }
            for(Dict dict : dictList){
                String selected = "";
                if(StringUtils.isNotEmpty(value) && dict.getValue().equals(value)){
                    selected = "selected";
                }
                writer.append("<option value='"+ dict.getValue() +"' "+selected+">"+ dict.getName() +"</option>");
            }
            writer.append("</select>");
        }else if(type.equals("radio")){
            //单选按钮
            for(Dict dict : dictList){
                String checked = "";
                if(StringUtils.isNotEmpty(value) && dict.getValue().equals(value)){
                    checked = "checked";
                }
                writer.append("<input type='radio' name='"+name+"' value='"+dict.getValue()+"' title='"+dict.getName()+"' "+checked+"> ");
            }
        }

        if(templateDirectiveBody != null){
            templateDirectiveBody.render(writer);
        }

    }

    protected String getParam(Map params, String name) {
        Object value = params.get(name);

        if (value instanceof SimpleScalar) {
            return ((SimpleScalar)value).getAsString();
        }

        return null;
    }
}
