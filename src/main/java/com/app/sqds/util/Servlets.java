package com.app.sqds.util;

import com.app.sqds.exception.SqdsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class Servlets {
    private static Logger logger = LoggerFactory.getLogger(Servlets.class);


    public static void initPageInfo(PageInfo pageInfo) {
        HttpServletRequest request = ParamUtils.getRequest();
        int page = ParamUtils.getInt("page",1);
        int size = ParamUtils.getInt("size",20);
        pageInfo.setPageNo(page);
        pageInfo.setPageSize(size);
        pageInfo.setOrderBy("id");
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
//            logger.debug("name = {}",name);
            String value = ParamUtils.getString(name,"");
            pageInfo.getPostValue().put(name, value);
            /*
            if (name.startsWith("_")) {
                if (name.equals("_orderBy")) {
                    pageInfo.setOrderBy(ParamUtils.getString(request, name, ""));
                } else if (name.equals("_order")) {
                    pageInfo.setOrder(ParamUtils.getString(request, name, "desc"));
                } else if (name.equals("_orderField")) {
                    pageInfo.setOrderField(ParamUtils.getString(request, name, ""));
                } else if (name.equals("_orderType")) {
                    pageInfo.setOrderType(ParamUtils.getString(request, name, "desc"));
                }
            } else {
//                Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
//                Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "");
//                pageInfo.setPostValue(searchParams);

            }

             */
        }
    }

    /**
     * 绑定用户提交的数据，通常用于表单的提交
     * @param command
     */
    public static void bind(Object command) {
        HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(command);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));
        binder.registerCustomEditor(Double.class, new CustomNumberEditor(Double.class, true));
        binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(Long.class, true));
        binder.registerCustomEditor(Float.class, null, new CustomNumberEditor(Float.class, true));
        binder.registerCustomEditor(BigDecimal.class, null, new CustomNumberEditor(BigDecimal.class, true));
        binder.registerCustomEditor(Byte.class, null, new CustomNumberEditor(Byte.class, true));
        binder.bind(request);
        BindingResult bindingResult = binder.getBindingResult();
        List list = bindingResult.getAllErrors();
        for (Object error : list) {
            logger.error(error.toString());
        }
        if (bindingResult.getErrorCount() > 0) {
            throw new SqdsException("您输入的数据有误。");
        }
    }

}
