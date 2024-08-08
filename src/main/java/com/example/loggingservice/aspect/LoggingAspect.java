package com.example.loggingservice.aspect;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Log4j2
public class LoggingAspect {

    @Pointcut("within(com.example.loggingservice.service.impl.*)")
    public void crudMethods() {
    }

    @Pointcut("@annotation(com.example.loggingservice.annotation.UserAction)")
    public void user() {
    }

    @Pointcut("@annotation(com.example.loggingservice.annotation.OrderAction)")
    public void order() {
    }

    @Pointcut("@annotation(com.example.loggingservice.annotation.Returning)")
    public void returning() {
    }

    @Around("crudMethods()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Выполнение метода {} с аргументами {}", methodName, args);

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        log.info("Метод {} выполнен с результатом {} за время {}мс", methodName, result, executionTime);

        return result;
    }

    @AfterReturning(value = "returning() && target(service)", returning = "result", argNames = "result,service")
    public void addLogAfterReturning(Object result, Object service) {
        log.info("После выполнения метода findById в сервисе {} был получен результат {}",
                service, result);
    }

    @AfterThrowing(value = "crudMethods()", throwing = "exception")
    public void addLogAfterThrowing(Throwable exception) {
        log.warn("Произошла ошибка: {}", exception.getMessage());
    }

    @Before("order()")
    public void addLogBeforeOrderAction() {
        log.info("Начало работы с заказом");
    }

    @After("order()")
    public void addLogAfterOrderAction() {
        log.info("Конец работы с заказом");
    }

    @Before("user()")
    public void addLogBeforeUserAction() {
        log.info("Начало работы с пользователем");
    }

    @After("user()")
    public void addLogAfterUserAction() {
        log.info("Конец работы с пользователем");
    }
}
