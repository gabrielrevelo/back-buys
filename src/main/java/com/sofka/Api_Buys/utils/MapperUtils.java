package com.sofka.Api_Buys.utils;

import com.sofka.Api_Buys.model.Buy;
import com.sofka.Api_Buys.model.BuyDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MapperUtils {

    public Function<BuyDTO, Buy> mapperToBuy(String id) {
        return updateBuy -> {
            var buy = new Buy();
            buy.setId(id);
            buy.setDate(updateBuy.getDate());
            buy.setIdType(updateBuy.getIdType());
            buy.setIdClient(updateBuy.getIdClient());
            buy.setClientName(updateBuy.getClientName());
            buy.setUsername(updateBuy.getUsername());
            buy.setProducts(updateBuy.getProducts());
            return buy;
        };
    }

    public Function<Buy, BuyDTO> mapEntityToBuy() {
        return entity -> new BuyDTO(
                entity.getId(),
                entity.getDate(),
                entity.getIdType(),
                entity.getIdClient(),
                entity.getClientName(),
                entity.getUsername(),
                entity.getProducts()
        );
    }
}
