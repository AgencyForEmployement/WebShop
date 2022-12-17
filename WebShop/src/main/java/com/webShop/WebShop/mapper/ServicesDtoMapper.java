package com.webShop.WebShop.mapper;

import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.dto.ServicesDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServicesDtoMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    Services fromServiceDtoToService(ServicesDto servicesDto);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "price", target = "price")
    ServicesDto fromServiceToServiceDto(Services services);
}
