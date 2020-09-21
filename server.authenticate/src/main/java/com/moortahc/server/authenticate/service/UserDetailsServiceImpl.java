package com.moortahc.server.authenticate.service;

import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
@Qualifier("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    
    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String emailAddress) throws UsernameNotFoundException {
        var opUserEntity = userRepository.findByEmailAddress(emailAddress);
        if (opUserEntity.isEmpty()) {
            throw new UsernameNotFoundException("Email address was not found");
        }
        var userEntity = opUserEntity.get();
        var grantedAuthorities = userEntity.getRoles().stream().map(UserRole::toSpringRole).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new User(userEntity.getId().toString(), userEntity.getPasswordHash(), grantedAuthorities);
    }
}
