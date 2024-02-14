package com.stoom.ecomm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "img_url")
    private String imgUrl;

    private Boolean active;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;

}