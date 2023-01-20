package com.shoes.shoes.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "shoes")
@ToString
public class Shoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Getter @Setter @Column(name = "model")
    private String model;

    @Getter @Setter @Column(name = "brand")
    private String brand;

    @Getter @Setter @Column(name="size")
    private Double size;

    @Getter @Setter @Column(name = "stock")
    private Integer stock;

}