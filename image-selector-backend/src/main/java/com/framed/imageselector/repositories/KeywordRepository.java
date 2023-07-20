package com.framed.imageselector.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.framed.imageselector.models.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    
}
