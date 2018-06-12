package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.database.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Integer> {
}