package com.framed.imageselector.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.framed.imageselector.models.CustomImagem;
import com.framed.imageselector.models.IdxCategoria;
import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.models.ImagemDto;
import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.services.ImagemService;
import com.framed.imageselector.services.KeywordService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/images")
public class ImagemController {

    private final ImagemService imagemService;
    private final KeywordService keywordService;
    private final KeywordController keywordController;

    @Autowired
    public ImagemController(ImagemService imagemService, KeywordService keywordService, KeywordController keywordController) {
        this.imagemService = imagemService;
        this.keywordService = keywordService;
        this.keywordController = keywordController;
    }

    @GetMapping("/raw-metadata")
    public ResponseEntity<Map<String, String>> getImageMetadata2(@RequestParam String url) {
        Map<String, String> result = imagemService.extractAllMetadataFromImage(url);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get-keywords")
    public ResponseEntity<List<String>> getImageMetadata(@RequestParam String url) {
        List<String> result = imagemService.getKeywordsFromUrl(url);
        return ResponseEntity.ok(result);
    }
    
    @PostMapping("/cadastrar")
    public ResponseEntity<Long> cadastrarImagem(@RequestParam String url, UriComponentsBuilder uriBuilder) {
        
        Long imagemId = imagemService.addImagem(url);
        
        if (imagemId == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        
        java.net.URI uri = uriBuilder.path("/cadastrar/{id}").buildAndExpand(imagemId).toUri();
        
        return ResponseEntity.created(uri).body(imagemId);
    }
    
    @PostMapping("/imagem-dto")
    public ResponseEntity<Boolean> receiveImagemDto(@RequestBody ImagemDto imagemDto) {
        Long imagemId = this.cadastrarImagem(imagemDto.getImagemUrl(), UriComponentsBuilder.newInstance()).getBody();
        if (imagemId == null) {
            return ResponseEntity.ok().body(false);
        }
        Arrays.asList(imagemDto.getTagsToRemove()).forEach(tag -> this.keywordController.removeCategoriaFromImage(imagemId, tag));
        Arrays.asList(imagemDto.getTagsToAdd()).forEach(tag -> this.imagemService.addCategoriaToImagem(imagemId, tag));
        return ResponseEntity.ok().body(true);
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

    @PostMapping("/add-categoria/")
    public ResponseEntity<Void> addCategoriaToImagem(@RequestParam Long id, @RequestParam String categoria) {
        boolean result = imagemService.addCategoriaToImagem(id, categoria);
        if (result) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/add-categoria/body")
    public ResponseEntity<Void> receiveNewTagsToImagem(@RequestBody List<IdxCategoria> results) {
        results.forEach(result -> imagemService.addCategoriaToImagem(result.getId(), result.getCategoria()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove-categoria/body")
    public ResponseEntity<Void> deleteTagsFromImagem(@RequestBody List<IdxCategoria> results) {
        results.forEach(result -> keywordController.removeCategoriaFromImage(result.getId(), result.getCategoria()));
        return ResponseEntity.ok().body(null);
    }


    @DeleteMapping("/delete-image/{id}")
    public ResponseEntity<Void> deleteImagem(@PathVariable Long id) {

    Optional<Imagem> imgToDelete = imagemService.findById(id);
    if (imgToDelete.isEmpty()) {
        return ResponseEntity.badRequest().build();
    }

    List<Keyword> keywordList = imgToDelete.get().getKeywords();

    imgToDelete.get().setKeywords(Collections.emptyList());
    imagemService.save(imgToDelete.get());

    keywordList.forEach(keyword -> {
        if (keyword.getImagens().isEmpty()) {
            keywordService.delete(keyword);
        }
    });

    imagemService.delete(imgToDelete.get());

    return ResponseEntity.ok().build();
}

    @GetMapping("/imagem/{id}")
    public ResponseEntity<CustomImagem> getById(@PathVariable Long id) {
        Optional<Imagem> img = this.imagemService.findById(id);
        if (img.isPresent()) {
            CustomImagem result = new CustomImagem(img.get().getId(), img.get().getUrl(), img.get().getKeywords());
            return ResponseEntity.ok().body(result);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/search-with-tags")
    public ResponseEntity<CustomImagem[]> getByTags(@RequestParam List<String> tag) {
        Optional<List<Imagem>> result = this.imagemService.findImagemWithTags(tag);
        if (result.isPresent()) {
            List<CustomImagem> resultToShow = new ArrayList<>();
            result.get().forEach(imagem -> resultToShow.add(new CustomImagem(imagem.getId(), imagem.getUrl(), imagem.getKeywords())));
            return ResponseEntity.ok().body(resultToShow.toArray(new CustomImagem[0]));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
