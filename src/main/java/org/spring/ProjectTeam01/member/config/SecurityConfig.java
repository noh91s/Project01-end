package org.spring.ProjectTeam01.member.config;

import lombok.RequiredArgsConstructor;
import org.spring.ProjectTeam01.member.config.handler.CustomAccessDeniedHandler;
import org.spring.ProjectTeam01.member.config.handler.CustomAuthEntryPointHandler;
import org.spring.ProjectTeam01.member.config.handler.CustomerOAuthSuccessHandler;
import org.spring.ProjectTeam01.member.config.handler.LoginFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


   

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());


        http
                .authorizeRequests()
                .antMatchers("/js/**","/css/**", "/images/**").permitAll()
                .antMatchers("/index","/", "/member/join","/password", "/findPassword").permitAll()
                .antMatchers("/member/insertInfo").hasRole("GUEST")
                .antMatchers("/member/login").permitAll()
                .antMatchers("/board/list").permitAll()
                .antMatchers("/item/list","/item/detail/**").permitAll()
                .antMatchers("/board/detail/**").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/member/logOut", "/member/detail/**", "/member/update/**").authenticated()
                .antMatchers("/cart/list","/cart/add").hasAnyRole("MEMBER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
        ;

        http
                .formLogin()
                .loginPage("/member/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/member/login")
                .defaultSuccessUrl("/")
                .failureHandler(loginFailHandler());
//                .and()
//                .oauth2Login()
//                .loginPage("/member/login")
//                .successHandler(customerOAuthSuccessHandler())
//                .userInfoEndpoint()
//                .userService(myAuth2UserService());

        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler())
                .authenticationEntryPoint(customAuthenticationEntryPoint())
        ;

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myAuth2UserService() {
        return new MyOAuth2UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public CustomAuthEntryPointHandler customAuthenticationEntryPoint() {
        return new CustomAuthEntryPointHandler();
    }

    @Bean
    public CustomerOAuthSuccessHandler customerOAuthSuccessHandler() {
        return new CustomerOAuthSuccessHandler();
    }


    @Bean
    public LoginFailHandler loginFailHandler(){
        return new LoginFailHandler();
    }

}


