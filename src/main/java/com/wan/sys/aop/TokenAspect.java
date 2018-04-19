package com.wan.sys.aop;

import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.ResponseFail;
import com.wan.sys.service.cityManager.impl.cityManagerServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class TokenAspect {

    @Around("@annotation(com.wan.sys.annotation.ValidToken)")
    public Object execute(ProceedingJoinPoint pjp) throws Throwable {

        HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String userId = r.getHeader("x-user-id");

        if (StringUtils.isNotEmpty(userId)) {
            Long id = Long.valueOf(userId);
            String token = r.getHeader("x-token");

            cityManagerServiceImpl service = new cityManagerServiceImpl();

            boolean isAuthorized = service.tokenCheck(id, token);
            if (isAuthorized) {
                return pjp.proceed();
            }
        }

        return new ResponseFail(ErrorCodeEnum.FAIL_TOKENLOSE);
    }
}
