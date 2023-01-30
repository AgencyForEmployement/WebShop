package com.webShop.WebShop.repository;

import com.webShop.WebShop.model.Transaction;
import com.webShop.WebShop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    @Query("select u from User u left join fetch u.transactions t where u.id = ?1 ")
    User findUserTransactions(long id);
}

