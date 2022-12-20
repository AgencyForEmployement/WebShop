package com.webShop.WebShop.controller;

import com.webShop.WebShop.WebShopApplication;
import com.webShop.WebShop.dto.ServicesDto;
import com.webShop.WebShop.mapper.ServicesDtoMapper;
import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.service.ServicesService;
import lombok.AllArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service")
@AllArgsConstructor
public class ServiceController {
    private final ServicesService servicesService;
    private final ServicesDtoMapper servicesDtoMapper;
    final static Logger log = Logger.getLogger(WebShopApplication.class.getName());

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping
    public ResponseEntity<List<ServicesDto>> getAll(){
        List<ServicesDto> servicesDto = new ArrayList<>();
        for (Services service : servicesService.getAll()){
            servicesDto.add(servicesDtoMapper.fromServiceToServiceDto(service));
            log.info("Accessing to all services.");
        }
        return new ResponseEntity<>(servicesDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping
    public ResponseEntity<ServicesDto> save(@RequestBody ServicesDto servicesDto){
        Services service = servicesService.save(servicesDtoMapper.fromServiceDtoToService(servicesDto));
        log.warn("Saving new services!");
        log.info("New service with service id " + service.getId() + " is created!");
        return new ResponseEntity<>(servicesDtoMapper.fromServiceToServiceDto(service), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable long id){
        Services serviceExisting = servicesService.getById(id);
        if (serviceExisting == null) {
            log.error("Service with service id " + serviceExisting.getId() + " not found.");
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        }
        log.warn("Deleting service with service id " + serviceExisting.getId());
        servicesService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
