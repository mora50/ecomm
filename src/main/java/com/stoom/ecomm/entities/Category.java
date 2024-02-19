package com.stoom.ecomm.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Set;

@Entity
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


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}