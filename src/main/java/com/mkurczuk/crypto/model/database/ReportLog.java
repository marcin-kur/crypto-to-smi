package com.mkurczuk.crypto.model.database;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "report_log")
public class ReportLog {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "crypto_id")
    @ManyToOne
    private Cryptocurrency cryptocurrency;

    @JoinColumn(name = "index_id")
    @ManyToOne
    private StockMarketIndex indexDictionary;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "datetime")
    private LocalDateTime dateTime;

    public ReportLog(Cryptocurrency cryptocurrency, BigDecimal value, LocalDateTime dateTime) {
        this.cryptocurrency = cryptocurrency;
        this.value = value;
        this.dateTime = dateTime;
    }

    public ReportLog(StockMarketIndex indexDictionary, BigDecimal value, LocalDateTime dateTime) {
        this.indexDictionary = indexDictionary;
        this.value = value;
        this.dateTime = dateTime;
    }
}
