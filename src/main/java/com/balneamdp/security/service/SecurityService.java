package com.balneamdp.security.service;

import com.balneamdp.models.SeaSideResort;
import com.balneamdp.repository.SeaSideResortRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final SeaSideResortRepository seaSideResortRepository;

    public boolean casAccessSeaSideResort(Long seaSideResortId, String loggedUsername){
        return seaSideResortRepository.findById(seaSideResortId)
                .map(s-> {
                    return s.getOwner().getEmail().equals(loggedUsername);
                })
                .orElse(false);
    }

}
