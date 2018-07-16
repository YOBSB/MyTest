package com.miniworld.utils.sessionaop;

import com.miniworld.common.GlobalManager;
import com.miniworld.common.ReMessage;
import com.miniworld.dao.SeasonMapper;
import com.miniworld.entity.Season;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
public class WorksAop {
    private final Logger log = LoggerFactory.getLogger(WorksAop.class);

    @Resource
    private GlobalManager globalManager;

    @Resource
    private SeasonMapper seasonMapper;

    @Pointcut("execution(* com.miniworld.controller.WorksShowController.*(..))")
    public void isEventInTimePointcut() {
    }

    @Pointcut("execution(* com.miniworld.controller.HonorTopController.pri*(..))")
    public void isHaveEventLiefPointcut() {
    }


    @Around("isEventInTimePointcut()")
    public Object isEventInTime(ProceedingJoinPoint pjp) throws Throwable {
        long now = System.currentTimeMillis();
        //log.info("now={},startTime={},endTime={}", now, globalManager.getEventSysStrartTime(), globalManager.getEventSysEndTime());
        Long startTime = globalManager.getEventSysStrartTime();
        Long endTime = globalManager.getEventSysEndTime();
        if (startTime != null && endTime != null && now > startTime && now < endTime) {
            return pjp.proceed();
        }
        String returnType = ((MethodSignature) pjp.getSignature()).getReturnType().getSimpleName();
        if (returnType.equals("ReMessage")) {
            log.info("赛季未开始或已结束:now={},startTime={},endTime={}", now, globalManager.getEventSysStrartTime(), globalManager.getEventSysEndTime());
            return new ReMessage<>(-2, "没有开启的赛季");
        }
        return pjp.proceed();

    }


    @Around("isHaveEventLiefPointcut()")
    public Object isHaveEventLief(ProceedingJoinPoint pjp) throws Throwable {
        Season season = seasonMapper.selectActiveSeason();
        if (season == null) {
            log.info("没有开启的赛季:");
            return new ReMessage<>(-2, "没有开启的赛季");
        }
        return pjp.proceed();

    }
}
