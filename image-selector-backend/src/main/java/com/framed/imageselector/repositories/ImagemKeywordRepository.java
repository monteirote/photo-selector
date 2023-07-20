package com.framed.imageselector.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framed.imageselector.models.ImagemKeyword;

public interface ImagemKeywordRepository extends JpaRepository <ImagemKeyword, Long> {
    
}
