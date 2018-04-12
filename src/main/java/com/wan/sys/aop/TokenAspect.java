package com.wan.sys.aop;

import com.wan.sys.annotation.ValidToken;
import com.wan.sys.pojo.ErrorCodeEnum;
import com.wan.sys.pojo.ResponseFail;
import com.wan.sys.service.cityManager.impl.cityManagerServiceImpl;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class TokenAspect {

    public Object execute(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature ms = (MethodSignature) pjp.getSignature();
        Method method = ms.getMethod();
        if (method.isAnnotationPresent(ValidToken.class)) {

            HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            Long id = Long.parseLong(r.getHeader("x-user-id"));
            String token = r.getHeader("x-token");

            cityManagerServiceImpl service = new cityManagerServiceImpl();

            boolean isAuthorized = service.tokenCheck(id, token);
            if (isAuthorized) {
                return pjp.proceed();
            }

            return new ResponseFail(ErrorCodeEnum.FAIL_TOKENLOSE);
        }

        return pjp.proceed();
    }
}
