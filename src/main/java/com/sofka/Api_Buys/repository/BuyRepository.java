package com.sofka.Api_Buys.repository;

import com.sofka.Api_Buys.collection.Buy;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BuyRepository extends ReactiveCrudRepository<Buy, String> {

}
