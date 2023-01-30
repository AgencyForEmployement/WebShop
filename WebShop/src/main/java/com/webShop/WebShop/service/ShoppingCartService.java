package com.webShop.WebShop.service;

import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.model.ShoppingCart;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ServicesService servicesService;


    public ShoppingCart addServiceToChart(Services service, User user){
        ShoppingCart cart = shoppingCartRepository.getById(user.getShoppingCart().getId());
        cart.getServices().add(service);
        cart.setAmount(countCartAmount(cart));
        cart = shoppingCartRepository.save(cart);
        return cart;
    }

    public ShoppingCart deleteServiceFromChart(long serviceId, User user){
        ShoppingCart cart = shoppingCartRepository.getById(user.getShoppingCart().getId());
        Services service = servicesService.getById(serviceId);
        cart.getServices().remove(service);
        cart.setAmount(countCartAmount(cart));
        cart = shoppingCartRepository.save(cart);
        return cart;
    }

    public double countCartAmount(ShoppingCart shoppingCart){
        double amount = 0;
        if(shoppingCart.getServices().size() > 0){
            for(Services s: shoppingCart.getServices()){
                amount += s.getPrice();
            }
        }
        return  amount;
    }
}
