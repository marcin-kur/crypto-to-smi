package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.PanelUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelUserRepository extends JpaRepository<PanelUser, Integer> {
}
