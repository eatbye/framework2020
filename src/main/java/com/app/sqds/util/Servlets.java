package com.app.sqds.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import java.util.Enumeration;
import java.util.Map;

public class Servlets {
    private static Logger logger = LoggerFactory.getLogger(Servlets.class);


    public static void initPageInfo(ServletRequest request, PageInfo pageInfo) {
        int page = ParamUtils.getInt(request,"page",1);
        int size = ParamUtils.getInt(request,"size",20);
        pageInfo.setPageNo(page);
        pageInfo.setPageSize(size);
        pageInfo.setOrderBy("id");
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            logger.debug("name = {}",name);
            String value = ParamUtils.getString(request,name,"");
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

}
