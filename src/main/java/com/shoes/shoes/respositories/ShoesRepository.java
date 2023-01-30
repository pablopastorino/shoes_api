package com.shoes.shoes.respositories;

import com.shoes.shoes.models.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {
    boolean existsByModelAndSize(String model, Double size);
    List<Shoes> findFirstByOrderBySizeDesc();
}