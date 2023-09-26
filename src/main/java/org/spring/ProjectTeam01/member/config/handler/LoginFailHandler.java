package org.spring.ProjectTeam01.member.config.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class LoginFailHandler implements AuthenticationFailureHandler{


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 인증 실패 시 메시지 생성
        String errorMessage = "로그인 실패. 사용자 이름 또는 비밀번호가 올바르지 않습니다.";

        // 메시지를 세션에 저장
        request.getSession().setAttribute("errorMessage", errorMessage);

        // 사용자를 로그인 페이지로 리디렉션하고 실패 메시지를 함께 전달
        response.sendRedirect("/member/login?error=true");

    }
}
