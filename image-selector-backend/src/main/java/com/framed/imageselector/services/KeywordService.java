package com.framed.imageselector.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.repositories.KeywordRepository;

@Service
public class KeywordService {

    private final KeywordRepository keywordRepository;

    @Autowired
    public KeywordService(KeywordRepository keywordRepository) {
        this.keywordRepository = keywordRepository;
    }

    public List<Keyword> getAllKeywords() {
        return this.keywordRepository.findAll();
    }

    public Keyword findByCategoria(String categoria) {
        return this.keywordRepository.findByCategoria(categoria);
    }

    public Keyword createKeyword(String categoria) {
        Keyword novaKeyword = keywordRepository.findByCategoria(categoria);

        if (novaKeyword == null) {
            novaKeyword = new Keyword(categoria.toLowerCase().replace(" ", "-"));
            novaKeyword = keywordRepository.save(novaKeyword);
        }

        return novaKeyword;
    }

    public void saveAll(List<Keyword> keywords) {
        this.keywordRepository.saveAll(keywords);
    }
 
    
}
