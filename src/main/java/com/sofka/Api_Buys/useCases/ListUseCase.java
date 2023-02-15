package com.sofka.Api_Buys.useCases;

import com.sofka.Api_Buys.model.BuyDTO;
import com.sofka.Api_Buys.repository.BuyRepository;
import com.sofka.Api_Buys.utils.MapperUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@Validated
public class ListUseCase implements Supplier<Flux<BuyDTO>> {
    private final BuyRepository buyRepository;

    private final MapperUtils mapperUtils;

    public ListUseCase(BuyRepository buyRepository, MapperUtils mapperUtils) {
        this.buyRepository = buyRepository;
        this.mapperUtils = mapperUtils;
    }

    @Override
    public Flux<BuyDTO> get() {
        return buyRepository.findAll()
                .map(mapperUtils.mapEntityToBuy());
    }
}
