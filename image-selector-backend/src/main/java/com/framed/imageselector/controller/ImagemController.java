package com.framed.imageselector.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.framed.imageselector.models.CustomImagem;
import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.services.ImagemService;
import com.framed.imageselector.services.KeywordService;

@RestController
@RequestMapping("/images")
public class ImagemController {

    private final ImagemService imagemService;
    private final KeywordService keywordService;

    @Autowired
    public ImagemController(ImagemService imagemService, KeywordService keywordService) {
        this.imagemService = imagemService;
        this.keywordService = keywordService;
    }

    @GetMapping("/get-keywords")
    public ResponseEntity<Map<String, String>> getImageMetadata(@RequestParam String imageUrl) {
        Map<String, String> result = imagemService.extractAllMetadataFromImage(imageUrl);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Void> cadastrarImagem(@RequestParam String url, UriComponentsBuilder uriBuilder) {

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
    public ResponseEntity<Void> addCategoriaToImagem(@RequestParam Long id, @RequestParam String categoria) {
        boolean result = imagemService.addCategoriaToImagem(id, categoria);
        if (result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // @DeleteMapping("/delete-image/{id}")
    // public ResponseEntity<Void> deleteImagem(@PathVariable Long id) {

    //     Optional<Imagem> imgToDelete = imagemService.findById(id);
    //     if (imgToDelete.isEmpty()) {
    //         return ResponseEntity.badRequest().build();
    //     }


    //     imgToDelete.get().getKeywords().forEach(keyword -> {
    //         if (keyword.getImagens().isEmpty()) {
    //             keywordService.delete(keyword);
    //         }
    //     });

    //     imagemService.delete(imgToDelete.get());
    //     return ResponseEntity.ok().build();

    // }

    @DeleteMapping("/delete-image/{id}")
public ResponseEntity<Void> deleteImagem(@PathVariable Long id) {

    Optional<Imagem> imgToDelete = imagemService.findById(id);
    if (imgToDelete.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    List<Keyword> keywordList = imgToDelete.get().getKeywords();

    // Remove a associação da imagem com as keywords
    imgToDelete.get().setKeywords(Collections.emptyList());
    imagemService.save(imgToDelete.get());

    // Exclui as keywords associadas à imagem
    keywordList.forEach(keyword -> {
        if (keyword.getImagens().isEmpty()) {
            keywordService.delete(keyword);
        }
    });

    // Exclui a imagem
    imagemService.delete(imgToDelete.get());

    return ResponseEntity.ok().build();
}


}
