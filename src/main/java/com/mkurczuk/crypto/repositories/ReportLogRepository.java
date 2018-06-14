package com.mkurczuk.crypto.repositories;

import com.mkurczuk.crypto.model.Cryptocurrency;
import com.mkurczuk.crypto.model.ReportLog;
import com.mkurczuk.crypto.model.StockMarketIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportLogRepository extends JpaRepository<ReportLog, Integer> {
    List<ReportLog> findReportLogsByCryptocurrencyIsOrIndexDictionaryIsAndDateTimeBetween(Cryptocurrency cryptocurrency, StockMarketIndex indexDictionary, LocalDateTime startDate, LocalDateTime endDate);
}
