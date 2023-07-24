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

    private final ImagemService imagemCadastradaService;

    @Autowired
    public ImagemController(ImagemService imagemCadastradaService) {
        this.imagemCadastradaService = imagemCadastradaService;
    }

    @GetMapping("/all-metadata")
    public Map<String, String> getImageMetadata(@RequestParam String imageUrl) {
        return imagemCadastradaService.extractAllMetadataFromImage(imageUrl);
    }

    @PostMapping("/cadastrar")
    public void cadastrarImagem(@RequestParam String url) {
        imagemCadastradaService.addImagem(url);
    }

    @GetMapping("/cadastradas")
    public List<Imagem> getAllImages() {
        return imagemCadastradaService.getAllImages();
    }


    
}
