package br.com.vinicius.photoselector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.vinicius.photoselector.models.ImagemCadastrada;

@Repository
public interface ImageRepository extends JpaRepository<ImagemCadastrada, Long> {
    
}
