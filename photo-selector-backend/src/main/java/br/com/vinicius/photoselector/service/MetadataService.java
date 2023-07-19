package br.com.vinicius.photoselector.service;

import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import br.com.vinicius.photoselector.ImageRepository;

@Service
public class MetadataService {

    private final ImageRepository imageRepository;

    public MetadataService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
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


    public String getKeywordsFromImage(String imageUrl) {
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
}

