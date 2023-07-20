package com.framed.imageselector.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.framed.imageselector.models.ImagemCadastrada;
import com.framed.imageselector.services.ImagemCadastradaService;

@RestController
@RequestMapping("/images")
public class ImagemCadastradaController {

    private final ImagemCadastradaService imagemCadastradaService;

    @Autowired
    public ImagemCadastradaController(ImagemCadastradaService imagemCadastradaService) {
        this.imagemCadastradaService = imagemCadastradaService;
    }

    @GetMapping("/all-metadata")
    public Map<String, String> getImageMetadata(@RequestParam String imageUrl) {
        return imagemCadastradaService.extractAllMetadataFromImage(imageUrl);
    }

    @GetMapping("/keywords")
    public String getImageKeywords (@RequestParam String imageUrl) {
        return imagemCadastradaService.getKeywords(imageUrl);
    }

    @PostMapping("/cadastrar")
    public boolean cadastrarImagem(@RequestParam String url) {
        return imagemCadastradaService.addImagem(url);
    }

    @GetMapping("/cadastradas")
    public List<ImagemCadastrada> getAllImages() {
        return imagemCadastradaService.getAllImages();
    }


    
}
