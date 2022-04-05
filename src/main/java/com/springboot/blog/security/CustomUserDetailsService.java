package com.springboot.blog.security;

import com.springboot.blog.model.Role;
import com.springboot.blog.model.User;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
//    private RoleRepository repository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
//        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
       User user = userRepository.findByEmailOrName(usernameOrEmail, usernameOrEmail)
               .orElseThrow(() ->
               new UsernameNotFoundException("User not found by name or email: "+ usernameOrEmail));
       return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapToAuthority(user.getRoleSet()));
    }

    private Collection<? extends GrantedAuthority> mapToAuthority(Set<Role> roles){

        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

    }
}
