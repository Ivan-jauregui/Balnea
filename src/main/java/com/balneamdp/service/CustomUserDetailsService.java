package com.balneamdp.service;

import com.balneamdp.exceptions.ResourseNotFoundException;
import com.balneamdp.models.CustomUserDetails;
import com.balneamdp.models.User;
import com.balneamdp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourseNotFoundException("Usuario no encontrado: " + username));;

        return new CustomUserDetails(user);
    }
}
