package com.shoes.shoes.repositories;

import com.shoes.shoes.models.Shoes;
import com.shoes.shoes.respositories.ShoesRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Comparator;
import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ShoesRepositoryTests {
    @Autowired
    private ShoesRepository shoesRepository;

    private Shoes shoes;
    private List<Shoes> initialShoes;

    @BeforeEach
    void setup(){
        initialShoes = shoesRepository.findAll();
    }

    @DisplayName("SAVE ONE")
    @Test
    void saveShoesTest(){
        var shoes = Shoes.builder().model("Test Model 1").brand("Test Brand 1").size(29.5).stock(10).build();

        var savedShoes = shoesRepository.save(shoes);

        Assertions.assertThat(savedShoes).isNotNull();
        Assertions.assertThat(savedShoes.getId()).isGreaterThan(0);
    }

    @DisplayName("LIST ALL")
    @Test
    void listAllTest(){
        List<Shoes> shoesList = shoesRepository.findAll();

        Assertions.assertThat(shoesList.size()).isEqualTo(initialShoes.size());

        var shoes1 = Shoes.builder().model("Test Model 1").brand("Test Brand 2").size(29.5).stock(10).build();
        var shoes2 = Shoes.builder().model("Test Model 2").brand("Test Brand 2").size(29.5).stock(10).build();

        shoesRepository.save(shoes1);
        shoesRepository.save(shoes2);

        shoesList = shoesRepository.findAll();

        Assertions.assertThat(shoesList.size()).isEqualTo(initialShoes.size() + 2);
    }

    @DisplayName("GET BY ID")
    @Test
    void getByIdTest(){
        var shoes1 = Shoes.builder().model("Test Model").brand("Test Brand").size(29.5).stock(10).build();
        var savedShoes = shoesRepository.save(shoes1);

        var recoveredShoes = shoesRepository.findById(savedShoes.getId());

        Assertions.assertThat(recoveredShoes.get().getId()).isEqualTo(savedShoes.getId());
    }

    @DisplayName("UPDATE ONE")
    @Test
    void updateOneTest(){
        var shoes1 = Shoes.builder().model("Test Model").brand("Test Brand").size(29.5).stock(10).build();
        var savedShoes = shoesRepository.save(shoes1);

        var updatedModel = "Updated Model";
        var updatedBrand = "Updated Brand";

        savedShoes.setBrand(updatedBrand);
        savedShoes.setModel(updatedModel);

        shoesRepository.save(savedShoes);

        var updatedShoes = shoesRepository.findById(savedShoes.getId());

        Assertions.assertThat(updatedShoes.get().getModel()).isEqualTo(updatedModel);
        Assertions.assertThat(updatedShoes.get().getBrand()).isEqualTo(updatedBrand);
    }

    @DisplayName("DELETE ONE")
    @Test
    void deleteOneTest(){
        var shoes1 = Shoes.builder().model("Test Model").brand("Test Brand").size(29.5).stock(10).build();
        var savedShoes = shoesRepository.save(shoes1);

        shoesRepository.delete(savedShoes);

        var deletedShoes = shoesRepository.findById(savedShoes.getId());

        Assertions.assertThat(deletedShoes.isPresent()).isFalse();
    }

    @DisplayName("GET MAX SIZE")
    @Test
    void getLargestTest(){
        var shoes = shoesRepository.findAll();
        var actualLargest = shoes.stream().max(Comparator.comparing(Shoes::getSize)).orElse(null);

        assert actualLargest != null;

        var actualLargestSize = actualLargest.getSize();

        var newLargestSize = actualLargestSize + 1;

        var newLargest = Shoes.builder().model("Largest Model").brand("Largest Brand").size(newLargestSize).stock(10).build();

        var newLargestSaved = shoesRepository.save(newLargest);

        var largestShoes = shoesRepository.findFirstByOrderBySizeDesc();

        Assertions.assertThat(largestShoes).contains(newLargestSaved);
    }
}
