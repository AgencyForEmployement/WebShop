package com.webShop.WebShop.repository;

import com.webShop.WebShop.model.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Services,Long>  {
    Services findById(long id);
}
