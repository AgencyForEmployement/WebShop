package com.webShop.WebShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webShop.WebShop.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @ManyToMany(fetch = FetchType.EAGER, cascade =  CascadeType.MERGE)
    @Fetch(FetchMode.SELECT)
    @JsonIgnore
    private List<Services> transactionServices;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private int merchantOrderId;
    @Column
    private LocalDateTime merchantOrderTimestamp;
    @Column
    private double amount;
    @Column
    private TransactionStatus status;
    @Column
    private String currency;
    @Column
    private String status;
    @Column
    private String paymentMethod;
    @Column
    private String paymentId;
}
