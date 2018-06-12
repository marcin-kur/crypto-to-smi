package com.mkurczuk.crypto.model.database;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @Column(name = "id")
    private int id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private PanelUser panelUser;

    @JoinColumn(name = "crypto_id")
    @ManyToOne
    private Cryptocurrency cryptocurrency;

    @JoinColumn(name = "index_id")
    @ManyToOne
    private StockMarketIndex indexDictionary;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}
