package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.database.StockMarketIndex;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMarketIndexRepository extends JpaRepository<StockMarketIndex, Integer> {
}