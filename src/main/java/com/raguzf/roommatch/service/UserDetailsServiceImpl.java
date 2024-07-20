package com.raguzf.roommatch.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.raguzf.roommatch.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional // when you load the user you load the authorities with it
public class UserDetailsServiceImpl implements UserDetailsService{
    private final UserRepository repository;
   
    @Override
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
    
}
