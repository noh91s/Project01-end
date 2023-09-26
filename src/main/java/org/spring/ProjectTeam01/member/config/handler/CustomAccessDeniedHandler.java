package org.spring.ProjectTeam01.member.config.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {


        boolean isMEMBER = request.isUserInRole("ROLE_MEMBER");
        boolean isGUEST = request.isUserInRole("ROLE_GUEST");
        boolean isANONYMOUS = request.isUserInRole("ROLE_ANONYMOUS");




        // JavaScript 코드로 팝업 메시지를 표시
        if (isMEMBER) {
            String errorMessage = "해당 페이지에 접근할 권한이 없습니다.";
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('" + errorMessage + "');window.location.href='/index?error=access-denied-for-members';</script>");
        } else if (isGUEST) {
            String errorMessage = "해당 페이지에 접근할 권한이 없습니다. 추가 정보를 입력해주세요.";
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('" + errorMessage + "');window.location.href='/member/insertInfo?error=access-denied-for-guests';</script>");
        }

    }

}



