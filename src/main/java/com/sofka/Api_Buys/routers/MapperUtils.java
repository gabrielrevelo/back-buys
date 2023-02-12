package com.sofka.Api_Buys.routers;

import com.sofka.Api_Buys.collection.Buy;
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
            buy.setProducts(updateBuy.getProducts());
            return buy;
        };
    }
}
