package com.framed.imageselector.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ImagemCadastrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String url;

    public ImagemCadastrada(String url) {
        this.url = url;
    }

    public ImagemCadastrada() {
    }

    public String getUrl() {
        return url;
    }
    
    
}
