package com.framed.imageselector.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.framed.imageselector.models.ImagemCadastrada;

public interface ImagemCadastradaRepository extends JpaRepository<ImagemCadastrada, Long> {
    
    ImagemCadastrada findByUrl(String url);

}
