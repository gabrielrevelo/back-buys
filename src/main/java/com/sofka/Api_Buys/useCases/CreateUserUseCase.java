package com.sofka.Api_Buys.useCases;

import com.sofka.Api_Buys.model.auth.User;
import com.sofka.Api_Buys.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> apply(User user) {
        return userRepository
                .save(user);
    }
}
