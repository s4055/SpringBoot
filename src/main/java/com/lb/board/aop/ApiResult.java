package com.lb.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ApiResult {

    @Pointcut("execution(* com.lb.board.controller..*(..))")
    private void pointcut(){};

    @AfterReturning(pointcut = "pointcut()", returning = "returnObj")
    public void Returning(JoinPoint joinPoint, Object returnObj) {
        log.info("응답 데이터 =========> " + returnObj.toString());
    }

    @AfterThrowing(pointcut = "pointcut()", throwing = "exceptObj")
    public void Throwing(JoinPoint joinPoint, Object exceptObj) {
        log.info("예외 발생 =========> " + exceptObj.toString());
    }

}
