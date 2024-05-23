package com.example.capstone_backend.common.jwt;

import com.example.capstone_backend.domain.user.entity.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@RequiredArgsConstructor
@Getter
public class CustomUserDetails implements UserDetails {
    private UserInfo userInfo;

    public CustomUserDetails(UserInfo byEmail) {
        this.userInfo = byEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "";
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        return userInfo.getUserPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getEmail();
    }

    public Long getUserId() {
        return userInfo.getId();
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
