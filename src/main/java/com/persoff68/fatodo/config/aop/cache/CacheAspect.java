package com.persoff68.fatodo.config.aop.cache;

import com.persoff68.fatodo.config.aop.cache.annotation.RedisCacheEvict;
import com.persoff68.fatodo.config.aop.cache.annotation.RedisCacheable;
import com.persoff68.fatodo.config.aop.cache.util.CacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class CacheAspect {

    private final CacheManager cacheManager;

    @Around("@annotation(redisCacheable)")
    public Object doRedisCacheable(ProceedingJoinPoint pjp, RedisCacheable redisCacheable) throws Throwable {
        Cache cache = cacheManager.getCache(redisCacheable.cacheName());
        Object key = getKey(pjp, redisCacheable.key());
        if (cache != null) {
            Object object = cache.get(key, getReturnType(pjp));
            if (object != null) {
                log.debug("Read from cache: {} - {}", redisCacheable.cacheName(), redisCacheable.key());
                return object;
            }
        }
        Object result = pjp.proceed();
        if (cache != null) {
            log.debug("Write to cache: {} - {}", redisCacheable.cacheName(), redisCacheable.key());
            cache.put(key, result);
        }
        return result;
    }

    @Around("@annotation(redisCacheEvict)")
    public Object doRedisCacheEvict(ProceedingJoinPoint pjp, RedisCacheEvict redisCacheEvict) throws Throwable {
        Cache cache = cacheManager.getCache(redisCacheEvict.cacheName());
        Object result = pjp.proceed();
        if (cache != null) {
            Object key = getKeyCollection(pjp, redisCacheEvict.key());
            Collection<?> collection = (Collection<?>) key;
            for (Object o : collection) {
                log.debug("Delete from cache: {} - {}", redisCacheEvict.cacheName(), redisCacheEvict.key());
                cache.evict(o);
            }
        }
        return result;
    }

    private static Class<?> getReturnType(ProceedingJoinPoint pjp) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        return methodSignature.getReturnType();
    }

    private static Object getKey(ProceedingJoinPoint pjp, String key) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();
        return CacheUtils.getValue(names, args, key);
    }

    private static Collection<?> getKeyCollection(ProceedingJoinPoint pjp, String key) {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        String[] names = methodSignature.getParameterNames();
        Object[] args = pjp.getArgs();
        return CacheUtils.getCollectionValue(names, args, key);
    }


}
