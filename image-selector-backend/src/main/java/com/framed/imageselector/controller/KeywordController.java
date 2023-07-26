package com.framed.imageselector.controller;

import java.util.Collections;
import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.services.KeywordService;

@RestController
@RequestMapping("/keywords")
public class KeywordController {

    private final KeywordService keywordService;

    public KeywordController(KeywordService keywordService) {
        this.keywordService = keywordService;
    }

    @GetMapping("/exibir/{categoria}")
    public ResponseEntity<List<Imagem>> getAllImagesFromCategoria(@PathVariable String categoria) {
        Keyword kw = this.keywordService.findByCategoria(categoria);
        if (kw == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(kw.getImagens());
    }

    

    
    
}
