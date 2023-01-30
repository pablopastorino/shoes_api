package com.shoes.shoes.services;

import com.shoes.shoes.models.Shoes;

import java.util.List;
import java.util.Optional;

public interface ShoesService {
    List<Shoes> findAll();

    Optional<Shoes> findById(Long id);

    Shoes save(Shoes shoes);

    void delete(Shoes shoes);

    Shoes update(Shoes shoes);

    List<Shoes> findFirstByOrderBySizeDesc();

    boolean existsByModelAndSize(String model, Double size);
}
