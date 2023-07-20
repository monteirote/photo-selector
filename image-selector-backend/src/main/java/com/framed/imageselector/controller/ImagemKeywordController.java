package com.framed.imageselector.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framed.imageselector.services.ImagemKeywordService;

@RestController
@RequestMapping("/image-keywords")
public class ImagemKeywordController {

    private final ImagemKeywordService imagemKeywordService;

    public ImagemKeywordController(ImagemKeywordService imagemKeywordService) {
        this.imagemKeywordService = imagemKeywordService;
    }
    
}
