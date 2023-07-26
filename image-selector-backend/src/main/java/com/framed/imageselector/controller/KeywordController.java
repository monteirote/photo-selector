package com.framed.imageselector.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framed.imageselector.models.CustomKeyword;
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
    public ResponseEntity<CustomKeyword> getAllImagesFromCategoria(@PathVariable String categoria) {
        Keyword kw = this.keywordService.findByCategoria(categoria);
        if (kw == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        CustomKeyword ckw = new CustomKeyword(kw.getId(), kw.getCategoria(), kw.getImagens());
        return ResponseEntity.ok().body(ckw);
    }

    

    
    
}
