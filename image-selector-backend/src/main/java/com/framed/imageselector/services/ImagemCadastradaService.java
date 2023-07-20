package com.framed.imageselector.services;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.framed.imageselector.models.ImagemCadastrada;
import com.framed.imageselector.repositories.ImagemCadastradaRepository;

import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

@Service
public class ImagemCadastradaService {

    private final ImagemCadastradaRepository imagemCadastradaRepository;

    @Autowired
    public ImagemCadastradaService(ImagemCadastradaRepository imagemCadastradaRepository) {
        this.imagemCadastradaRepository = imagemCadastradaRepository;
    }

    public Map<String, String> extractAllMetadataFromImage(String imageUrl) {
        Map<String, String> metadataMap = new HashMap<>();
        Map<String, String> metadataKeywords = new HashMap<>();

        try {
            URL url = new URL(imageUrl);
            Metadata metadata = ImageMetadataReader.readMetadata(url.openStream());

            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();
                    String tagDescription = tag.getDescription();
                    if ("Keywords".equals(tagName)) {
                        metadataKeywords.put(tagName, tagDescription);
                    }
                    metadataMap.put(tagName, tagDescription);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler a imagem: " + e.getMessage());
        } catch (ImageProcessingException e) {
            System.out.println("Erro de processamento da imagem: " + e.getMessage());
        }

        return metadataKeywords;
    }

    public String getKeywords(String imageUrl) {
        String metadataKeywords = "";
        try {
            URL url = new URL(imageUrl);
            Metadata metadata = ImageMetadataReader.readMetadata(url.openStream());
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagName().equals("Keywords")) {
                        metadataKeywords = tag.getDescription();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler a imagem: " + e.getMessage());
        } catch (ImageProcessingException e) {
            System.out.println("Erro de processamento da imagem: " + e.getMessage());
        }
        if (metadataKeywords.equals("")) {
            return "Essa imagem não possui keywords.";
        } else {
            return metadataKeywords;
        }

    }

    public void delete(ImagemCadastrada imagemCadastrada) {
        this.imagemCadastradaRepository.delete(imagemCadastrada);
    }

    public boolean addImagem(String imageUrl) {
        if (imagemCadastradaRepository.findByUrl(imageUrl) != null) {
            return false;
        }

        imagemCadastradaRepository.save(new ImagemCadastrada(imageUrl));
        return true;
    }

    public List<ImagemCadastrada> getAllImages() {
        return imagemCadastradaRepository.findAll();
    }

}


