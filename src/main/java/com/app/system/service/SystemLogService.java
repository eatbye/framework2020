package com.app.system.service;

import com.app.sqds.hibernate.HibernateDao;
import com.app.sqds.util.IPUtil;
import com.app.sqds.util.PageInfo;
import com.app.sqds.util.StringUtils;
import com.app.system.model.SystemLog;
import com.app.system.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 系统操作日志
 */
@Service
public class SystemLogService extends HibernateDao<SystemLog> {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 日志保存，异步
     * @param point
     * @param method
     * @param request
     * @param operation
     * @param start
     */
    @Async
    public void saveLog(ProceedingJoinPoint point, Method method, HttpServletRequest request, String operation, long start) {
        SystemLog systemLog = new SystemLog();
        // 设置 IP地址
        String ip = IPUtil.getIpAddress(request);
        systemLog.setIp(ip);
        // 设置操作用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null) {
            systemLog.setUsername(user.getUsername());
        }
        // 设置耗时
        systemLog.setTime(System.currentTimeMillis() - start);
        // 设置操作描述
        systemLog.setOperation(operation);
        // 请求的类名
        String className = point.getTarget().getClass().getName();
        // 请求的方法名
        String methodName = method.getName();
        systemLog.setMethod(className + "." + methodName + "()");
        // 请求的方法参数值
        Object[] args = point.getArgs();
        // 请求的方法参数名称
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        if (args != null && paramNames != null) {
            StringBuilder params = new StringBuilder();
            params = handleParams(params, args, Arrays.asList(paramNames));
            String paramsString = params.toString();
            if(paramsString.length()>128){
                paramsString = paramsString.substring(0,127);
            }
            systemLog.setParams(paramsString);

        }
        systemLog.setCreateTime(new Date());
        // 保存系统日志
        save(systemLog);
    }

    private StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
    }

    public PageInfo logList(PageInfo pageInfo) {
        List<Object> data = new Vector<Object>();
        String hql = "from SystemLog l where";
        String username = pageInfo.getPostStringValue("username");
        if(StringUtils.isNotEmpty(username)){
            hql += " username  like ? and ";
            data.add("%"+username+"%");
        }
        hql += " 1=1 order by id desc";
        return list(pageInfo,hql, data.toArray());
    }
}
