package com.webShop.WebShop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.webShop.WebShop.enums.UserType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private long id;
    @Column
    private UserType roleName;
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    public Role(UserType roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return roleName.toString();
    }
}
