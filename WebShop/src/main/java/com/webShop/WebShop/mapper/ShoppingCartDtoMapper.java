package com.webShop.WebShop.mapper;

import com.webShop.WebShop.dto.ShoppingCartDto;
import com.webShop.WebShop.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ShoppingCartDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "services", target = "services")
   @Mapping(source = "amount", target = "amount")
    ShoppingCart fromDtoToShoppingCart(ShoppingCartDto shoppingCartDto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "services", target = "services")
    @Mapping(source = "amount", target = "amount")
    ShoppingCartDto fromShoppingCartToDto(ShoppingCart shoppingCart);
}
