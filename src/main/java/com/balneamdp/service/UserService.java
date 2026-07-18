package com.balneamdp.service;

import com.balneamdp.mapper.UserMapper;
import com.balneamdp.models.SeaSideResort;
import com.balneamdp.models.User;
import com.balneamdp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User save(User user){
        return userRepository.save(user);
    }

    @Transactional
    public boolean registerUserForResort(User user,SeaSideResort seaSideResort){
        return seaSideResort.getClients().add(user);
    }
}
