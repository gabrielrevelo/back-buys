package com.sofka.Api_Buys.useCases;

import com.sofka.Api_Buys.model.auth.User;
import com.sofka.Api_Buys.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindUserUseCase {

    private UserRepository userRepository;

    public FindUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
