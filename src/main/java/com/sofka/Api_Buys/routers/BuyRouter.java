package com.sofka.Api_Buys.routers;

import com.sofka.Api_Buys.model.BuyDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BuyRouter {

    String url = "http://localhost:9090";

    WebClient.Builder builder = WebClient.builder();

    @Bean
    public RouterFunction<ServerResponse> create(CreateUseCase createUseCase) {
        Function<BuyDTO, Mono<ServerResponse>> executor = buyDTO ->
                Mono.just(buyDTO.getProducts())
                        .flatMapIterable(productToBuy -> productToBuy)
                        .flatMap(productToBuy ->
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


}

class ProductDTO {

    private String id;
    @NotBlank
    private String name;
    @NotNull
    private Integer inventory;

    @NotBlank
    private String imageUrl;

    private Boolean enabled;

    private Integer min;

    private Integer max;

    public ProductDTO(String id, String name, Integer inventory, String imageUrl, Boolean enabled, Integer min, Integer max) {
        this.id = id;
        this.name = name;
        this.inventory = inventory;
        this.imageUrl = imageUrl;
        this.enabled = enabled;
        this.min = min;
        this.max = max;
    }

    public ProductDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}
