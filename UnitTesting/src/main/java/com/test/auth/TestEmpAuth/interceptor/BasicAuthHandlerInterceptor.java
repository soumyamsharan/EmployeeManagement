package com.test.auth.TestEmpAuth.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
public class BasicAuthHandlerInterceptor implements HandlerInterceptor
{
    // --header 'AUTHORIZATION: Basic c291bXlhbTpzaGFyYW4=' \

    private static final String username = "soumyam";
    private static final String password = "sharan";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("BasicAuthHandlerInterceptor::preHandle()");

        String authHeader = request.getHeader("AUTHORIZATION");

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Basic "))
        {
            String base64Creds = authHeader.substring("Basic ".length());
            byte[] decodedCreds = Base64.getDecoder().decode(base64Creds);
            String creds = new String(decodedCreds);

            String[] parts = creds.split(":");

            if (username.equals(parts[0]) && password.equals(parts[1]))
            {
                return true;
            }
        }

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        log.info("BasicAuthHandlerInterceptor::postHandle()");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        log.info("BasicAuthHandlerInterceptor::afterCompletion()");
    }
}
