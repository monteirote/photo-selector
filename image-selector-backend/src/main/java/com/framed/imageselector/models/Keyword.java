package com.framed.imageselector.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Keyword {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String categoria;

    @ManyToMany(mappedBy = "keywords")
    private List<Imagem> imagensCadastradas = new ArrayList<>();

    public Keyword(String categoria) {
        this.categoria = categoria;
    }

    public Keyword() {}


    public String getCategoria() {
        return categoria;
    }

    public List<Imagem> getImagensCadastradas() {
        return this.imagensCadastradas;
    }

    public void addImagemCadastrada(Imagem imagemCadastrada) {
        if (imagensCadastradas == null) {
            imagensCadastradas = new ArrayList<>();
        }
        imagensCadastradas.add(imagemCadastrada);
        imagemCadastrada.getKeywords().add(this);
    }

    public void removeImagemCadastrada(Imagem imagemCadastrada) {
        imagensCadastradas.remove(imagemCadastrada);
        imagemCadastrada.getKeywords().remove(this);
    }

}
