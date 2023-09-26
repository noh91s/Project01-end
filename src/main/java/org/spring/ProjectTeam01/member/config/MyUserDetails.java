package org.spring.ProjectTeam01.member.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.spring.ProjectTeam01.member.entity.MemberEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
public class MyUserDetails implements UserDetails, OAuth2User {

    private MemberEntity memberEntity;
    private Map<String, Object> attributes;

    // 일반 회원
    public MyUserDetails(MemberEntity memberEntity){
        this.memberEntity = memberEntity;
    }

    // OAuth2 인증 회원
    public MyUserDetails(MemberEntity memberEntity, Map<String, Object> attributes){
        this.memberEntity = memberEntity;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes(){
        return attributes;
    }

    @Override
    public String getName() {
        return memberEntity.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectionRole = new ArrayList<>();
        collectionRole.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_" + memberEntity.getRole().toString();
            }
        });

        return collectionRole;
    }

    @Override
    public String getPassword() {
        return memberEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return memberEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
