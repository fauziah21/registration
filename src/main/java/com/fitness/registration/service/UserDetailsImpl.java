package com.fitness.registration.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitness.registration.model.AccountModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private Long accountId;
    private String accountName;
    private String accountEmail;
    private String accountPassword;
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long accountId, String accountName, String accountEmail, String accountPassword, Collection<? extends GrantedAuthority> authorities) {
        this.accountId = accountId;
        this.accountName = accountName;
        this.accountEmail = accountEmail;
        this.accountPassword = accountPassword;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(AccountModel accountModel) {
        List<GrantedAuthority> authorities = accountModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
                accountModel.getAccountId(),
                accountModel.getAccountName(),
                accountModel.getAccountEmail(),
                accountModel.getAccountPassword(),
                authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getAccountId(){
        return accountId;
    }

    public String getAccountEmail(){
        return accountEmail;
    }

    @Override
    public String getPassword() {
        return accountPassword;
    }

    @Override
    public String getUsername() {
        return accountName;
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
        return Objects.equals(accountId, user.accountId);
    }
}
