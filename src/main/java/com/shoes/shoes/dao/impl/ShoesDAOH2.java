package com.shoes.shoes.dao.impl;

import com.shoes.shoes.dao.ShoesDAO;
import com.shoes.shoes.models.Shoes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Transactional
public class ShoesDAOH2 implements ShoesDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Shoes> getAll() {
        return entityManager.createQuery("FROM Shoes").getResultList();
    }

    @Override
    public Shoes getOne(Long id) {
        return entityManager.find(Shoes.class, id);
    }

    @Override
    public void create(Shoes shoes) {
        entityManager.merge(shoes);
    }

    @Override
    public void delete(Long id) {
        var shoes = entityManager.find(Shoes.class, id);
        entityManager.remove(shoes);
    }

    @Override
    public Shoes update(Shoes shoes) {
        return entityManager.merge(shoes);
    }

    @Override
    public List getLargests() {
        String query = "SELECT * FROM shoes WHERE size = SELECT MAX(size) FROM shoes;"; // wildcard is not the best practice

        return entityManager.createNativeQuery(query).getResultList();
    }
}
