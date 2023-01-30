package com.shoes.shoes.controllers;

import com.shoes.shoes.services.ShoesService;
import com.shoes.shoes.dtos.ShoesDTO;
import com.shoes.shoes.models.Shoes;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/shoes")
public class ShoesController {
    @Autowired
    private ShoesService shoesService;

    @GetMapping
    public ResponseEntity<Object> getAll() {

        return ResponseEntity.status(HttpStatus.OK).body(shoesService.findAll());

    }

    @GetMapping(value = "/max-size")
    public ResponseEntity<Object> getLargests() {

        return ResponseEntity.status(HttpStatus.OK).body(shoesService.findFirstByOrderBySizeDesc());

    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> getOne(@PathVariable Long id) {

        Optional<Shoes> shoesOptional = shoesService.findById(id);

        if (shoesOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(shoesOptional.get());
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ShoesDTO shoes) {
        var alreadyExists = shoesService.existsByModelAndSize(shoes.getModel(), shoes.getSize());

        if(alreadyExists) return ResponseEntity.status(HttpStatus.CONFLICT).body("Shoes " + shoes.getModel() + " size " + shoes.getSize() + " already exists.");

        var shoesModel = new Shoes();

        BeanUtils.copyProperties(shoes, shoesModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(shoesService.save(shoesModel));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {

        Optional<Shoes> shoesModelOptional = shoesService.findById(id);

        if (shoesModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found");
        }

        shoesService.delete(shoesModelOptional.get());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Shoes deleted successfully.");

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid ShoesDTO shoes) {

        Optional<Shoes> shoesModelOptional = shoesService.findById(id);

        if (!shoesModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shoes not found");
        }

        var shoesModel = new Shoes();

        BeanUtils.copyProperties(shoes, shoesModel);

        shoesModel.setId(shoesModelOptional.get().getId());

        var newShoes = shoesService.update(shoesModel);

        return ResponseEntity.status(HttpStatus.OK).body(newShoes);

    }

}
