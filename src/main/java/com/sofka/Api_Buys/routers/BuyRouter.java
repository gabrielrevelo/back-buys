package com.sofka.Api_Buys.routers;

import com.sofka.Api_Buys.model.BuyDTO;
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
public class BuyRouter {

    @Bean
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<BuyDTO, Mono<ServerResponse>> executor = buyDTO -> createUseCase.apply(buyDTO)
                .flatMap(result -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(result));

        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BuyDTO.class).flatMap(executor)
        );
    }


}
