package com.mt.common.core.aspectj;

import com.mt.common.core.shiro.session.ShiroSessionRepository;
import com.mt.upms.common.model.User;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author:Wujie
 * @Date: Create in 2018-03-05 13:47
 * @Description:
 * @Modified By:
 */

@Aspect
@Component
public class AspectjLogger {

    @Autowired
    ShiroSessionRepository shiroSessionRepository;

    private static Logger logger = LoggerFactory.getLogger(AspectjLogger.class);

    @Pointcut("execution(public * com.mt.*.*.service.impl.*.*(..))")
    public void performance(){

    }

    /**
     * 前置通知
     * @param joinPoint
     */
    @Before("performance()")
    public void doBefore (JoinPoint joinPoint){
        StringBuilder sb = new StringBuilder();
        sb.append("Type : ").append("beforeLogger").append("\r\n");
        //获取request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取登录User
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null ){
            sb.append("Uid : ").append(user.getUid()).append("\r\n");
        }
        //类名
        sb.append("Controller : ").append(joinPoint.getSignature().getDeclaringTypeName()).append("\r\n");
        //方法名
        sb.append("Method : ").append(joinPoint.getSignature().getName()).append("\r\n");
        //请求方式
        sb.append("RequestMethod : ").append(request.getMethod()).append("\r\n");
        //请求参数
        sb.append("Params : ").append(joinPoint.getArgs()).append("\r\n");
        //URL
        sb.append("URI : ").append(request.getRequestURL()).append("\r\n");
        //ip
        sb.append("RequestIp : ").append(request.getRemoteHost()).append("\r\n");
        logger.info(sb.toString());

    }

    /**
     * 返回通知
     * @param joinPoint
     * @param result
     */
    @AfterReturning(value = "performance()",returning = "result")
    public void doAfterReturning(JoinPoint joinPoint,Object result){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("Type : ").append("afterReturningLogger").append("\r\n");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null){
            sb.append("Uid : ").append(user.getUid()).append("\r\n");
        }
        sb.append("Controller : ").append(joinPoint.getSignature().getDeclaringTypeName()).append("\r\n");
        sb.append("Method : ").append(joinPoint.getSignature().getName()).append("\r\n");
        sb.append("RequestMethod : ").append(request.getMethod()).append("\r\n");
        sb.append("URI : ").append(request.getRequestURL()).append("\r\n");
        sb.append("Return_Params").append(result).append("\r\n");
        logger.info(sb.toString());
    }

    /**
     * 异常通知
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "performance()",throwing="e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("Type : ").append("afterThrowingLogger").append("\r\n");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null){
            sb.append("Uid : ").append(user.getUid()).append("\r\n");
        }
        sb.append("Controller : ").append(joinPoint.getSignature().getDeclaringTypeName()).append("\r\n");
        sb.append("Method : ").append(joinPoint.getSignature().getName()).append("\r\n");
        sb.append("RequestMethod : ").append(request.getMethod()).append("\r\n");
        sb.append("URI : ").append(request.getRequestURL()).append("\r\n");
        sb.append("Message : ").append(e.getMessage()).append("\r\n");
        logger.error(sb.toString());
    }
}
