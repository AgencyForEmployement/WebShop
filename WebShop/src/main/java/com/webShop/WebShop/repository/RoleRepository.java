package com.webShop.WebShop.repository;

import com.webShop.WebShop.enums.UserType;
import com.webShop.WebShop.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(UserType userType);
}