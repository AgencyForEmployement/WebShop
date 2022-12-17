package com.webShop.WebShop.service;

import com.webShop.WebShop.model.Services;
import com.webShop.WebShop.repository.ServiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServicesService {
    private final ServiceRepository serviceRepository;

    public List<Services> getAll(){
        return serviceRepository.findAll();
    }

    public Services save(Services services){
        return serviceRepository.save(services);
    }

    public void deleteById(Long id){
        serviceRepository.deleteById(id);
    }

    public Services getById(long id) {
        return serviceRepository.findById(id);
    }
}
