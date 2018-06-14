package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.Cryptocurrency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CryptocurrencyRepository extends JpaRepository<Cryptocurrency, Integer> {
    Optional<Cryptocurrency> findCryptocurrencyByName(String name);
}