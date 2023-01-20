package com.shoes.shoes.controllers;

import com.shoes.shoes.dao.ShoesDAO;
import com.shoes.shoes.models.Shoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/shoes")
public class ShoesController {
    @Autowired
    private ShoesDAO shoesDAO;

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(shoesDAO.getAll());
    }

    @GetMapping(value = "/search", params = "size")
    public ResponseEntity<Object> getLargests() {
        return ResponseEntity.status(HttpStatus.OK).body(shoesDAO.getLargests());

    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {

        Optional<Shoes> shoesOptional = Optional.ofNullable(shoesDAO.getOne(id));
        if (!shoesOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(shoesOptional.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody Shoes shoes) {
        shoesDAO.create(shoes);

        return ResponseEntity.status(HttpStatus.CREATED).body("Shoes created successfully.");
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        Optional<Shoes> shoesModelOptional = Optional.ofNullable(shoesDAO.getOne(id));

        if (!shoesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found");
        }
        shoesDAO.delete(shoesModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Shoes deleted successfully.");

    }

    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Shoes shoes) {
        Optional<Shoes> shoesModelOptional = Optional.ofNullable(shoesDAO.getOne(shoes.getId()));

        if (!shoesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found.");
        }

        shoesDAO.update(shoes);
        return ResponseEntity.status(HttpStatus.OK).body("Shoes updated successfully.");

    }

}
