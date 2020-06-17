package com.employeemanagement.aspect;

import com.employeemanagement.constant.StringConstant;
import com.employeemanagement.utils.LogUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Aspect
@Component
public class LogAspect {
    Logger log = LoggerFactory.getLogger(LogAspect.class);
    ObjectMapper mapper = new ObjectMapper();

    @Around("controllerPointCut()")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return handleControllerLog(joinPoint);
    }

    private Object handleControllerLog(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String requestId = Optional.ofNullable(request.getHeader("Request-Id"))
                .orElse(LogUtil.generateRequestId());
        MDC.put(StringConstant.REQUEST_ID, requestId);
        String apiName = request.getRequestURI();
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        log.info(className +", Api invoked : "+ apiName + ", Request : "+ mapper.writeValueAsString(joinPoint.getArgs()));
        Object responseObject = joinPoint.proceed();
        log.info(className +", Exiting api : "+ apiName + ", Response : "+ mapper.writeValueAsString(responseObject));
        return responseObject;
    }

    @Around("@annotation(com.employeemanagement.annotation.MethodLog)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        Object[] args = joinPoint.getArgs();
        log.info(className +", Method invoked "+ methodName + ", Arguments : "+ mapper.writeValueAsString(args));
        Object responseObject = joinPoint.proceed();
        log.info(className +", Exiting method "+ methodName + ", Response : "+ mapper.writeValueAsString(responseObject));
        return responseObject;
    }

    @Pointcut("execution(* com.employeemanagement.controllers.*.*(..))")
    public void controllerPointCut(){
    }


}
