package com.framed.imageselector.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.repositories.ImagemCadastradaRepository;

import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

@Service
public class ImagemService {

    private final ImagemCadastradaRepository imagemCadastradaRepository;
    private final KeywordService keywordService;

    @Autowired
    public ImagemService(ImagemCadastradaRepository imagemCadastradaRepository,
            KeywordService keywordService) {
        this.imagemCadastradaRepository = imagemCadastradaRepository;
        this.keywordService = keywordService;
    }

    public Map<String, String> extractAllMetadataFromImage(String imageUrl) {
        Map<String, String> metadataKeywords = new HashMap<>();

        try {
            URL url = new URL(imageUrl);
            Metadata metadata = ImageMetadataReader.readMetadata(url.openStream());

            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    String tagName = tag.getTagName();
                    if ("Keywords".equals(tagName)) {
                        String tagDescription = tag.getDescription();
                        metadataKeywords.put(tagName, tagDescription);
                    }
                }
            }
        } catch (IOException | ImageProcessingException erro) {
            System.out.println("Erro ao ler a imagem: " + erro.getMessage());
        }

        return metadataKeywords;
    }

    public List<String> getKeywords(Imagem imagem) {
        try {
            URL url = new URL(imagem.getUrl());
            Metadata metadata = ImageMetadataReader.readMetadata(url.openStream());
            for (Directory directory : metadata.getDirectories()) {
                for (Tag tag : directory.getTags()) {
                    if (tag.getTagName().equals("Keywords")) {
                        return Arrays.asList(tag.getDescription().split(";"));
                    }
                }
            }
        } catch (IOException | ImageProcessingException erro) {
            System.out.println("Erro ao ler os metadados da imagem: " + erro.getMessage());
        }
        return Collections.emptyList();

    }

    public void delete(Imagem imagemCadastrada) {
        this.imagemCadastradaRepository.delete(imagemCadastrada);
    }

    public void addImagem(String imageUrl) {
        Imagem novaImagem = createImagemCadastrada(imageUrl);
        imagemCadastradaRepository.save(novaImagem);
        List<String> keywords = getKeywords(novaImagem);
        keywords.forEach(data -> {
            Keyword kw = keywordService.createKeyword(data);
            novaImagem.addKeyword(kw);
            kw.addImagemCadastrada(novaImagem);
        });
    }

    public List<Imagem> getAllImages() {
        return imagemCadastradaRepository.findAll();
    }

    private boolean urlIsValid(String url) {
        try {
            new java.net.URL(url);
            return true;
        } catch (java.net.MalformedURLException e) {
            return false;
        }
    }

    private Imagem createImagemCadastrada(String url) {
        if (imagemCadastradaRepository.findByUrl(url) != null || !urlIsValid(url)) {
            return imagemCadastradaRepository.findByUrl(url);
        }
        return imagemCadastradaRepository.save(new Imagem(url));
    }

}
