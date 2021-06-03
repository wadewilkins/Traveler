
//TravelerCRUDAspect.java

package com.wwilkins.traveler.traveler;

import com.wwilkins.traveler.TravelerApplication;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Component
@Aspect
public class TravelerCrudAspect {
    //private static final Logger logger = LoggerFactory.getLogger(TravelerApplication.class);
    private static final Logger logger = LoggerFactory.getLogger(TravelerCrudAspect.class);

    @Around("execution(* TravelerService.*(..))")         //point-cut expression
    public Object logAfterV1(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            Object result = joinPoint.proceed();
            ResponseEntity<Traveler> t = (ResponseEntity<Traveler>) result;
            int scv = t.getStatusCodeValue();
            if( scv <200 || scv >= 400){
                logger.info("Error on Exit: {}.{}() with result = {}", joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument: {} in {}.{}()", Arrays.toString(joinPoint.getArgs()),
                    joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
            throw e;
        }
    }
}


