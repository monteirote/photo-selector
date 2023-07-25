package com.framed.imageselector.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.framed.imageselector.models.Imagem;
import com.framed.imageselector.models.Keyword;
import com.framed.imageselector.repositories.ImagemRepository;

import java.net.URL;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

@Service
public class ImagemService {

    private final ImagemRepository imagemRepository;
    private final KeywordService keywordService;

    @Autowired
    public ImagemService(ImagemRepository imagemRepository, KeywordService keywordService) {
        this.imagemRepository = imagemRepository;
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

    public void delete(Imagem imagem) {
        this.imagemRepository.delete(imagem);
    }

    public String addImagem(String imageUrl) {

        Imagem imagem = createImagem(imageUrl);
        if (imagem == null) {
            return "Essa url não é válida.";
        }

        List<String> palavrasChave = getKeywords(imagem);
        List<Keyword> keywords = new ArrayList<>();

        for (String palavra : palavrasChave) {
            Keyword novaKeyword = keywordService.createKeyword(palavra);
            keywords.add(novaKeyword);
        }


        for (Keyword keyword : keywords) {
            imagem.addKeyword(keyword);
        }

        imagemRepository.save(imagem);
        return "Processo de cadastro concluído.";
    }

    private Imagem createImagem(String url) {

        if (urlIsNotValid(url)) {
            return null;
        }

        Imagem novaImagem = imagemRepository.findByUrl(url);
        

        if (novaImagem == null) {
            novaImagem = new Imagem(url);
            novaImagem = imagemRepository.save(novaImagem);
        }

        return novaImagem;
    }

    public List<Imagem> getAllImages() {
        return imagemRepository.findAll();
    }

    private boolean urlIsNotValid(String url) {
        try {
            new java.net.URL(url);
            return false;
        } catch (java.net.MalformedURLException e) {
            return true;
        }
    }
}
