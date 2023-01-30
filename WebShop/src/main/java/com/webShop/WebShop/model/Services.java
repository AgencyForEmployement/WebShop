package com.webShop.WebShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "services")
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double price;
    @ManyToMany(mappedBy = "services")
    @JsonIgnore
    private List<ShoppingCart> shoppingCartList = new ArrayList<>();
    @ManyToMany(mappedBy = "transactionServices")
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();
}
