package com.shoes.shoes.dao;

import com.shoes.shoes.models.Shoes;

import java.util.List;

public interface ShoesDAO {
    List<Shoes> getAll();

    Shoes getOne(Long id);

    void create(Shoes shoes);

    void delete(Long id);

    Shoes update(Shoes shoes);

    List<Shoes> getLargests();
}
