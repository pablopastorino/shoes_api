package com.shoes.shoes.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class ShoesDTO {
    @Getter
    @Setter
    @NotEmpty
    private String model;

    @Getter @Setter @NotEmpty
    private String brand;

    @Getter @Setter @NotNull
    private Double size;

    @Getter @Setter
    private Integer stock;
}
