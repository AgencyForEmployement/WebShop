package com.webShop.WebShop.service;

import com.webShop.WebShop.enums.UserType;
import com.webShop.WebShop.model.Role;
import com.webShop.WebShop.model.ShoppingCart;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.repository.RoleRepository;
import com.webShop.WebShop.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ShoppingCartService shoppingCartService;
    private final PasswordEncoder bCryptPasswordEncoder;


    public User save(User user) {
        Role role = roleRepository.findByRoleName(UserType.ROLE_USER);
        if (role == null)
            role = roleRepository.save(new Role(UserType.ROLE_USER));
        user.setRole(role);
        user.setEnabled(true);
        user.setShoppingCart(new ShoppingCart());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User userWithTransactions(long id) { return userRepository.findUserTransactions(id);}

}
