package com.sofka.Api_Buys.useCases;

import com.sofka.Api_Buys.collection.Buy;
import com.sofka.Api_Buys.model.BuyDTO;
import com.sofka.Api_Buys.repository.BuyRepository;
import com.sofka.Api_Buys.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Service
@Validated
public class CreateUseCase {
    private final BuyRepository buyRepository;

    private final MapperUtils mapperUtils;

    public CreateUseCase(BuyRepository buyRepository, MapperUtils mapperUtils) {
        this.buyRepository = buyRepository;
        this.mapperUtils = mapperUtils;
    }

    public Mono<Buy> apply(@Valid BuyDTO newProduct) {
        return buyRepository
                .save(mapperUtils.mapperToBuy(null).apply(newProduct));
    }
}
