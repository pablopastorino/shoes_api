package com.shoes.shoes.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shoes.shoes.models.Shoes;
import com.shoes.shoes.services.impl.ShoesServiceImp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ShoesControllerTests {
    private final String API_URL = "/api/shoes";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShoesServiceImp shoesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("CREATE")
    void saveShoesTest() throws Exception {
        Shoes shoes = Shoes.builder().model("Test Model").brand("Test Brand").size(10.0).stock(999).build();

        given(shoesService.save(shoes)).willAnswer(InvocationOnMock::getArguments);

        var response = mockMvc.perform(post(API_URL).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(shoes)));

        response.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    @DisplayName("GET ALL")
    void listAllShoesTest() throws Exception {
        List<Shoes> shoesList = new ArrayList<>();

        shoesList.add(Shoes.builder().model("Test Model").brand("Test Brand").size(10.0).stock(999).build());

        given(shoesService.findAll()).willReturn(shoesList);

        ResultActions response = mockMvc.perform(get(API_URL));

        response.andExpect(status().isOk()).andDo(print()).andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(shoesList.size()));
    }

    @Test
    @DisplayName("GET BY ID")
    void getShoesByIdTest() throws Exception {
        long shoesId = 1L;

        var shoes = Shoes.builder().model("Test Model").brand("Test Brand").size(10.0).stock(999).build();

        given(shoesService.findById(shoesId)).willReturn(Optional.ofNullable(shoes));

        ResultActions response = mockMvc.perform(get("/api/shoes/{id}", shoesId));

        response.andExpect(status().isOk()).andDo(print()).andExpect(MockMvcResultMatchers.content().string(containsString("Test Model")));

    }

    @Test
    @DisplayName("UPDATE")
    void updateShoesTest() throws Exception {
        long shoesId = 1L;
        Shoes savedShoes = Shoes.builder()
                .model("Saved Shoes")
                .brand("Saved Brand")
                .size(10.0)
                .stock(100)
                .build();

        Shoes updatedShoes = Shoes.builder()
                .model("Updated Shoes")
                .brand("Updated Brand")
                .size(11.0)
                .stock(99)
                .build();

        given(shoesService.findById(shoesId)).willReturn(Optional.of(savedShoes));
        given(shoesService.update(any(Shoes.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/shoes/{id}", shoesId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updatedShoes)));


        response.andExpect(status().isOk())
                .andExpect(status().isOk()).andDo(print()).andExpect(MockMvcResultMatchers.content().string(containsString("Updated Shoes")));

    }

    @Test
    @DisplayName("DELETE")
    void deleteShoesTest() throws Exception {
        Long shoesId = 1L;

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/shoes/{id}",shoesId));

        response.andExpect(status().isNotFound())
                .andDo(print()).andExpect(MockMvcResultMatchers.content().string(containsString("Shoes not found")));
    }
}
