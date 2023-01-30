package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.dto.ServicesDto;
import com.webShop.WebShop.dto.ShoppingCartDto;
import com.webShop.WebShop.mapper.ServicesDtoMapper;
import com.webShop.WebShop.mapper.ShoppingCartDtoMapper;
import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.model.ShoppingCart;
import com.webShop.WebShop.model.User;
import com.webShop.WebShop.service.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopping-cart")
@AllArgsConstructor
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartDtoMapper shoppingChartMapper;
    private final ServicesDtoMapper servicesMapper;
    final static Logger log = Logger.getLogger(WebShopApplication.class.getName());

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<ShoppingCartDto> getUserCart(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        ShoppingCartDto cart = shoppingChartMapper.fromShoppingCartToDto(user.getShoppingCart());
        log.info("Loading user cart...");
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping()
    public ResponseEntity<ShoppingCartDto> addServiceToCart(@RequestBody ServicesDto serviceDto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        ShoppingCart chart = shoppingCartService.addServiceToChart(servicesMapper.fromServiceDtoToService(serviceDto), user);
        log.info("New service with service id " + serviceDto.id + " added tp cart");
        return new ResponseEntity<>(shoppingChartMapper.fromShoppingCartToDto(chart), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/service/{id}")
    public ResponseEntity<ShoppingCartDto> removeServiceFromCart(@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();
        log.info("Service with service id " + id + " removed from user cart. Cart id " + user.getShoppingCart().getId());
        return new ResponseEntity<>(shoppingChartMapper.fromShoppingCartToDto(shoppingCartService.deleteServiceFromChart(id, user)), HttpStatus.OK);
    }
}
