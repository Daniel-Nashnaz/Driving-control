package com.test.testttt.x;


import java.util.*;
import java.util.stream.Collectors;

import com.test.entity.UserPassword;
import com.test.entity.Users;
import com.test.repository.UserPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    //@Autowired
//static
//UserPasswordRepository passwordRepository;
    private Integer id;

    private String username;

    private String email;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Integer id, String username, String email, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(Users user) {
        Set<GrantedAuthority> authorities = user.getUserVsRoles().stream().map((role) -> new SimpleGrantedAuthority(role.getRoleID().getRuleName().toString())).collect(Collectors.toSet());


        //Optional<UserPassword> password = Optional.ofNullable(passwordRepository.findByUserIDAndIsActiveFalse(user));

        String pass = user.getUserPasswords().stream().findFirst().get().getPassword();
        return new UserDetailsImpl(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                pass,
                //user.getUserPasswords().stream().findFirst().get().getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
