package com.mykola.railroad.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private final @Getter Integer id;
    private final @Getter String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(AuthenticatedUserInfo authInfo) {
        this.id = authInfo.getId();
        this.email = authInfo.getEmail(); // TODO
        this.password = authInfo.getPassword(); // TODO
        this.authorities = authInfo.getAcls()
                .stream()
                .map(
                        acl -> new SimpleGrantedAuthority(acl.name()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
