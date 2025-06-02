package com.mykola.railroad.security;

import com.mykola.railroad.service.AuthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthInfoService authInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Customer login
        AuthenticatedUserInfo authInfo = authInfoService.getAuthenticatedEmployeeInfo(username);
        return new UserDetailsImpl(authInfo);
    }
}
