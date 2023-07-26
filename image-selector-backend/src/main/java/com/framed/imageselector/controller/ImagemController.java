package com.framed.imageselector.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.framed.imageselector.models.CustomImagem;
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

    @GetMapping("/keywords")
    public ResponseEntity<Map<String, String>> getImageMetadata(@RequestParam String imageUrl) {
        Map<String, String> result = imagemService.extractAllMetadataFromImage(imageUrl);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarImagem(@RequestParam String url,  UriComponentsBuilder uriBuilder) {

        Long imagemId = imagemService.addImagem(url);
        
        if (imagemId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        java.net.URI uri = uriBuilder.path("/cadastrar/{id}").buildAndExpand(imagemId).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/all-images")
    public ResponseEntity<List<CustomImagem>> getAllImages() {
        List<Imagem> allImages = imagemService.getAllImages();
        List<CustomImagem> results = new ArrayList<>();
        allImages.forEach(imagem -> {
            CustomImagem img = new CustomImagem(imagem.getId(), imagem.getUrl(), imagem.getKeywords());
            results.add(img);
        });

        return ResponseEntity.ok(results);
    }

    @GetMapping("/add-categoria")
    public void addCategoriaToImagem(@RequestParam Long id, @RequestParam String categoria) {
        imagemService.addCategoriaToImagem(id, categoria);
    }


    
}
