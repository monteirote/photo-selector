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

    public Keyword createKeyword(String keyword) {
        if (keywordRepository.findByCategoria(keyword) != null) {
            return keywordRepository.findByCategoria(keyword);
        }
        
        return keywordRepository.save(new Keyword(keyword));
    }


    public List<Keyword> getAllKeywords() {
        return this.keywordRepository.findAll();
    }
 
    
}
