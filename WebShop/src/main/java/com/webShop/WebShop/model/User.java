package com.webShop.WebShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private String name;
    @Column
    private String surname;
    @Column(unique = true)
    private String email;
    @Column
    private String password;
    @Column
    private boolean enabled;
    @Column
    private String telephoneNumber;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Address address;
    @Column
    private String education;
    @Column
    private String experience;
    @OneToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private ShoppingCart shoppingCart;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<Transaction> transactions;
    @ManyToOne(fetch = FetchType.EAGER,cascade =  CascadeType.ALL)
    private Role role;
    public User(String name, String surname, String email, String password, String telephoneNumber, Address address, String education, String experience) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.telephoneNumber = telephoneNumber;
        this.address = address;
        this.education = education;
        this.experience = experience;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> collection = new ArrayList<Role>();
        collection.add(this.role);
        return collection;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}