package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.StockMarketIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockMarketIndexRepository extends JpaRepository<StockMarketIndex, Integer> {
    public Optional<StockMarketIndex> findStockMarketIndexByName(String name);

}