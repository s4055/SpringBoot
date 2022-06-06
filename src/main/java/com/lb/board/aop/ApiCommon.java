package com.lb.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class ApiCommon {

    @Pointcut("execution(* com.lb.board.controller..*(..))")
    private void pointcut(){};

    @Around("pointcut()")
    public Object Common(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        try {
            stopWatch.start();

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            log.info("URI : {}", request.getRequestURI());
            log.info("요청 데이터 [{}] =========> {}", joinPoint.getSignature().getName(), joinPoint.getArgs());

            return joinPoint.proceed();
        } finally {
            stopWatch.stop();

            log.info("수행 시간 =========> {}", stopWatch.getTotalTimeMillis());
        }
    }
}
