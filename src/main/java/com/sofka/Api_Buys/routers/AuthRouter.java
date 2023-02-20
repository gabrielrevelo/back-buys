package com.sofka.Api_Buys.routers;

import com.sofka.Api_Buys.model.auth.User;
import com.sofka.Api_Buys.model.auth.security.AuthResponse;
import com.sofka.Api_Buys.security.JWTUtil;
import com.sofka.Api_Buys.useCases.CreateUserUseCase;
import com.sofka.Api_Buys.useCases.FindUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AuthRouter {

    JWTUtil jwtUtil;

    public AuthRouter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public RouterFunction<ServerResponse> createUser(CreateUserUseCase createUserUseCase) {
        Function<User, Mono<ServerResponse>> executor = user -> createUserUseCase.apply(user)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/register").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class).flatMap(executor)
        );
    }

    @Bean
    public RouterFunction<ServerResponse> loginRouter(FindUserUseCase findUserUseCase) {
        Function<User, Mono<ServerResponse>> executor = user -> findUserUseCase.findByUsername(user.getUsername())
                .flatMap(userDetails -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(
                                new AuthResponse(userDetails.getUsername(), userDetails.getRoles(), jwtUtil.generateToken(userDetails))
                        ));

        return route(
                POST("/login").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(User.class).flatMap(executor)
        );
    }

}
