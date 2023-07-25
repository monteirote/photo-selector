package com.framed.imageselector.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Imagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String url;

    @ManyToMany
    @JoinTable(name = "imagem_keyword",
               joinColumns = @JoinColumn(name = "imagem_id"),
               inverseJoinColumns = @JoinColumn(name = "keyword_id"))
    private List<Keyword> keywords = new ArrayList<>();

    public Imagem(String url) {
        this.url = url;
    }

    public Imagem() {}

    public String getUrl() {
        return url;
    }

    public List<Keyword> getKeywords() {
        return this.keywords;
    }

    public void addKeyword(Keyword keyword) {
        keywords.add(keyword);
        keyword.getImagens().add(this);
    }

    public void removeKeyword(Keyword keyword) {
        keywords.remove(keyword);
        keyword.getImagens().remove(this);
    }
    
    
}
