package com.framed.imageselector.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.services.ImagemService;

@RestController
@RequestMapping("/images")
public class ImagemController {

    private final ImagemService imagemService;

    @Autowired
    public ImagemController(ImagemService imagemService) {
        this.imagemService = imagemService;
    }

    @GetMapping("/all-metadata")
    public Map<String, String> getImageMetadata(@RequestParam String imageUrl) {
        return imagemService.extractAllMetadataFromImage(imageUrl);
    }

    @PostMapping("/cadastrar")
    public void cadastrarImagem(@RequestParam String url) {
        imagemService.addImagem(url);
    }

    @GetMapping("/all-images")
    public List<Imagem> getAllImages() {
        return imagemService.getAllImages();
    }


    
}
