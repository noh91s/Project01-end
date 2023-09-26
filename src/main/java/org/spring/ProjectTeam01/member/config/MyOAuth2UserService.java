package org.spring.ProjectTeam01.member.config;

import org.spring.ProjectTeam01.contrant.Role;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.spring.ProjectTeam01.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class MyOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientId = clientRegistration.getClientId();
        String registrationId = clientRegistration.getRegistrationId();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        return snsUserAccess(oAuth2User, registrationId, attributes);
    }

    private OAuth2User snsUserAccess(OAuth2User oAuth2User, String registrationId, Map<String, Object> attributes) {

        String email = "";

        if (registrationId.equals("google")) {
            email = oAuth2User.getAttribute("email");

        } else if (registrationId.equals("naver")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            email = (String) response.get("email");

        } else if (registrationId.equals("kakao")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
            email = (String) response.get("email");
        }


        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByEmail(email);
        if (optionalMemberEntity.isPresent()) {
            return new MyUserDetails(optionalMemberEntity.get());
        }
        MemberEntity memberEntity = memberRepository.save(MemberEntity.builder()
                .email(email)
                .password(passwordEncoder.encode("password"))
                .role(Role.GUEST)
                .build());
        return new MyUserDetails(memberEntity, oAuth2User.getAttributes());
    }
}


