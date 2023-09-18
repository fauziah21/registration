package com.fitness.registration.service;

import com.fitness.registration.model.AccountModel;
import com.fitness.registration.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AccountRepository accountRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String accountEmail) throws UsernameNotFoundException {
        AccountModel user = accountRepository.findByAccountEmail(accountEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + accountEmail));

        return UserDetailsImpl.build(user);
    }
}
