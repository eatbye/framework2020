package com.app.system.aspect;

import com.app.sqds.util.ParamUtils;
import com.app.system.service.SystemLogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 系统操作日志
 */
@Aspect
@Component
public class SystemLogAspect  extends AspectSupport{
    private Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    @Autowired
    private SystemLogService systemLogService;

    /**
     * 系统日志记录
     * @param point
     * @return
     */
    @Around("@annotation(org.apache.shiro.authz.annotation.RequiresPermissions)")
    public Object systemLog(ProceedingJoinPoint point){
        Method targetMethod = resolveMethod(point);
        Object ret = null;
        RequiresPermissions annotation = targetMethod.getAnnotation(RequiresPermissions.class);
        String[] values = annotation.value();
        String operation = "";
        for(String value : values){
            operation += value;
        }

        long start = System.currentTimeMillis();
        try {
            ret = point.proceed(point.getArgs());
            HttpServletRequest request = ParamUtils.getRequest();
            systemLogService.saveLog(point, targetMethod, request, operation, start);
        }catch (Throwable throwable){

        }
        return ret;
    }

}
