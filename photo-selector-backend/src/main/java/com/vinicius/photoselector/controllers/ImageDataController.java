package com.vinicius.photoselector.controllers;

import java.io.InputStream;
import java.net.URL;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;

@RestController
public class ImageDataController {
    

    @GetMapping("/imagemetadados")
    public Metadata getImageData(@RequestParam("url") String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            InputStream inputStream = url.openStream();
            return ImageMetadataReader.readMetadata(inputStream);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }
}
