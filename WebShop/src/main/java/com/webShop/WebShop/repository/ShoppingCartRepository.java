package com.webShop.WebShop.repository;

import com.webShop.WebShop.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {

//    @Query("select sc from ShoppingCart sc where sc.user.id = ?1")
//    ShoppingCart getUserCart(long userId);

    ShoppingCart getById(long id);
}
