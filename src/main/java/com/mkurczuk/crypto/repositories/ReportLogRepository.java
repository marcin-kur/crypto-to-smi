package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.database.ReportLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportLogRepository extends JpaRepository<ReportLog, Integer> {
}
