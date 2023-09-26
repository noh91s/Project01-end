package org.spring.ProjectTeam01.member.config.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String errorMessage = "해당 페이지에 접근할 권한이 없습니다. 로그인후 이용해주세요.";
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write("<script>alert('" + errorMessage + "');window.location.href='/member/login?error=access-denied-for-nones';</script>");



    }
}
