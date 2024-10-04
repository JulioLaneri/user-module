package com.api.distri.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Aspect
@Component
public class TransactionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(TransactionLoggingAspect.class);

    @Pointcut("@annotation(transactional)")
    public void transactionalMethods(Transactional transactional) {}

    // Log antes de que una transacción inicie
    @Before("transactionalMethods(transactional)")
    public void logBeforeTransaction(JoinPoint joinPoint, Transactional transactional) {
        logger.info("Inicio de la transacción en el método: {}", joinPoint.getSignature().getName());
        logger.info("Propagación: {}", transactional.propagation());
        logger.info("ReadOnly: {}", transactional.readOnly());
        logger.info("Isolation: {}", transactional.isolation());
        logger.info("Timeout: {} segundos", transactional.timeout());
        logger.info("Parámetros: {}", Arrays.toString(joinPoint.getArgs()));
    }

    // Log para capturar el flujo de la transacción y medir su duración
    @Around("transactionalMethods(transactional)")
    public Object logTransactionDuration(ProceedingJoinPoint joinPoint, Transactional transactional) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            logger.info("Transacción exitosa en el método: {} (Duración: {} ms)", joinPoint.getSignature().getName(), duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            logger.error("Error en la transacción en el método: {} (Duración: {} ms)", joinPoint.getSignature().getName(), duration);
            throw ex;
        }
    }

    // Log para errores en las transacciones, incluyendo parámetros
    @AfterThrowing(pointcut = "transactionalMethods(transactional)", throwing = "error")
    public void logTransactionError(JoinPoint joinPoint, Transactional transactional, Throwable error) {
        logger.error("Excepción en la transacción del método: {}. Error: {}", joinPoint.getSignature().getName(), error.getMessage());
        logger.error("Propagación: {}", transactional.propagation());
        logger.error("ReadOnly: {}", transactional.readOnly());
        logger.error("Isolation: {}", transactional.isolation());
        // Loguear los parámetros de la transacción fallida
        logger.error("Parámetros en la transacción fallida: {}", Arrays.toString(joinPoint.getArgs()));
    }
}
