package com.shoes.shoes.service;

import com.shoes.shoes.models.Shoes;
import com.shoes.shoes.respositories.ShoesRepository;
import com.shoes.shoes.services.impl.ShoesServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ShoesServiceTests {
    @Mock
    private ShoesRepository shoesRepository;

    @InjectMocks @Autowired
    private ShoesServiceImp shoesService;

    private Shoes shoes;

    @BeforeEach
    void setup(){
        shoes = Shoes.builder().id(15L).model("Test Shoes").brand("Test Brand").size(10.0).stock(10).build();
    }

    @DisplayName("SAVE")
    @Test
    void saveShoesTest(){
        given(shoesRepository.save(shoes)).willReturn(shoes);

        var savedShoes = shoesService.save(shoes);

        assertThat(savedShoes).isNotNull();
    }

    @DisplayName("SAVE EXISTING")
    @Test
    void saveExistingShoesTest(){
        var saveExistingShoesResult = shoesService.save(shoes);

        assertThat(saveExistingShoesResult).isNull();
    }

    @DisplayName("FIND ALL")
    @Test
    void listAllShoesTest(){
        var initialShoes = shoesService.findAll().size();

        var newShoes = Shoes.builder().id(1L).model("Test Shoes").brand("Test Brand").size(10.0).stock(10).build();

        shoesService.save(newShoes);

        var newShoesLength = shoesService.findAll().size();

        assertThat(newShoesLength).isEqualTo(initialShoes); //FIXME
    }

    @DisplayName("FIND BY ID")
    @Test
    void getTestById(){
        given(shoesRepository.save(shoes)).willReturn(shoes);

        var savedShoes = shoesService.save(shoes);

        var result = shoesService.findById(savedShoes.getId());

        assertThat(result).isNotNull();

    }
}
