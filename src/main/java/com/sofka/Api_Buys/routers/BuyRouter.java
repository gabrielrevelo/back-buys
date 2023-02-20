package com.sofka.Api_Buys.routers;

import com.sofka.Api_Buys.model.BuyDTO;
import com.sofka.Api_Buys.model.ProductDTO;
import com.sofka.Api_Buys.useCases.CreateUseCase;
import com.sofka.Api_Buys.useCases.ListUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BuyRouter {

    String url = "http://localhost:9090";

    WebClient.Builder builder = WebClient.builder();

    @Bean
    @RouterOperation(
            path = "/create",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.POST,
            beanClass = CreateUseCase.class,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "apply",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Producto creado",
                                    content = @Content(schema = @Schema(
                                            implementation = ProductDTO.class
                                    )
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<BuyDTO, Mono<ServerResponse>> executor = buyDTO ->
                Mono.just(buyDTO.getProducts())
                        .flatMapIterable(productToBuy -> productToBuy)
                        .flatMap(productToBuy ->
                                // WebClient
                                builder.build()
                                        .get()
                                        .uri(url + "/get/" + productToBuy.getIdProduct())
                                        .retrieve()
                                        .bodyToMono(ProductDTO.class)
                                        .filter(prod ->
                                                // Verificaciones ------------------------------------------------------
                                                prod.getInventory() > productToBuy.getQuantity()
                                                && (prod.getInventory() - productToBuy.getQuantity()) > prod.getMin()
                                                //                      7                                     10
                                                && prod.getMax() >= productToBuy.getQuantity()
                                        ).map(product -> {
                                            int updatedInventory = product.getInventory() - productToBuy.getQuantity();
                                            product.setInventory(updatedInventory);
                                            return product;
                                        })
                        )
                        .collectList()
                        .flatMap(filteredProducts -> {
                            Mono<ServerResponse> response;

                            if (filteredProducts.size() == buyDTO.getProducts().size()) {
                                response = Mono.just(filteredProducts)
                                        .flatMapIterable(productsToBuy -> productsToBuy)
                                        .flatMap(prod ->
                                                builder.build()
                                                        .put()
                                                        .uri(url + "/update")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .body(Mono.just(prod), ProductDTO.class)
                                                        .retrieve()
                                                        .bodyToMono(String.class)
                                        )
                                        .then(createUseCase.apply(buyDTO))
                                        .flatMap(result -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(result));
                            } else {
                                response = ServerResponse.badRequest().build();
                            }

                            return response;
                        });


        return route(
                POST("/create").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BuyDTO.class).flatMap(executor)
        );
    }

    @Bean
    @RouterOperation(
            path = "/list",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE
            },
            method = RequestMethod.GET,
            beanClass = ListUseCase.class,
            beanMethod = "get",
            operation = @Operation(
                    operationId = "get",
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Lista de productos",
                                    content = @Content(array =
                                    @ArraySchema(schema = @Schema(
                                            implementation = BuyDTO.class)
                                    )
                                    )
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getAll(ListUseCase listUseCase) {
        return route(GET("/list"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(listUseCase.get(), BuyDTO.class))
        );
    }

}
