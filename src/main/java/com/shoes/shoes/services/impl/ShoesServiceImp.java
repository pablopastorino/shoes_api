package com.shoes.shoes.services.impl;

import com.shoes.shoes.respositories.ShoesRepository;
import com.shoes.shoes.services.ShoesService;
import com.shoes.shoes.models.Shoes;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShoesServiceImp implements ShoesService {
    final ShoesRepository shoesRepository;

    public ShoesServiceImp(ShoesRepository shoesRepository) {
        this.shoesRepository = shoesRepository;
    }

    @Override
    public List<Shoes> findAll() {
        return shoesRepository.findAll();
    }

    @Override
    public Optional<Shoes> findById(Long id) {
        return shoesRepository.findById(id);
    }

    @Override
    @Transactional
    public Shoes save(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    @Override
    @Transactional
    public void delete(Shoes shoes) {
        shoesRepository.delete(shoes);
    }

    @Override
    @Transactional
    public Shoes update(Shoes shoes) {
        return shoesRepository.save(shoes);
    }

    @Override
    public List<Shoes> findFirstByOrderBySizeDesc() {
        return shoesRepository.findFirstByOrderBySizeDesc();
    }

    @Override
    public boolean existsByModelAndSize(String model, Double size) {
        return shoesRepository.existsByModelAndSize(model, size);
    }

}
