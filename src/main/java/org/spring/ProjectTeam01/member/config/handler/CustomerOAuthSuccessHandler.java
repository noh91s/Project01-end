package org.spring.ProjectTeam01.member.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class CustomerOAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_GUEST"))) {
            // ABC ROLE을 가지고 있는 경우 /oauthMember 페이지로 리다이렉션
            getRedirectStrategy().sendRedirect(request, response, "/member/insertInfo");
        } else {
            // 다른 ROLE을 가지고 있는 경우 기본 설정된 URL로 리다이렉션
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
