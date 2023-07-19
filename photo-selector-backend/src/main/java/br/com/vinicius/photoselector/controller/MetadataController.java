package br.com.vinicius.photoselector.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.vinicius.photoselector.service.MetadataService;

@RestController
public class MetadataController {

    private final MetadataService metadataService;

    @Autowired
    public MetadataController(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @GetMapping("/all-metadata")
    public Map<String, String> getImageMetadata(@RequestParam String imageUrl) {
        return metadataService.extractAllMetadataFromImage(imageUrl);
    }

    @GetMapping("/keywords")
    public String getImageKeywords (@RequestParam String imageUrl) {
        return metadataService.getKeywordsFromImage(imageUrl);
    }
}
