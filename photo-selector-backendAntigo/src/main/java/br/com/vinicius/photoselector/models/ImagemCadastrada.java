package br.com.vinicius.photoselector.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class ImagemCadastrada {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String url;
    
}
