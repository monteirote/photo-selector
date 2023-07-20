package com.framed.imageselector.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.framed.imageselector.repositories.ImagemKeywordRepository;

@Service
public class ImagemKeywordService {

    private final ImagemKeywordRepository imagemKeywordRepository;

    @Autowired
    public ImagemKeywordService(ImagemKeywordRepository imagemKeywordRepository) {
        this.imagemKeywordRepository = imagemKeywordRepository;
    }
    
}
