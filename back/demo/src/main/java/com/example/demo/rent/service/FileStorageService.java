package com.example.demo.rent.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

  private static final String UPLOADS_DIR = "./uploads/images/";

  public String saveFile(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("Le fichier est vide");
    }
    if (!file.getContentType().startsWith("image/")) {
      throw new IllegalArgumentException("Le fichier n'est pas une image");
    }

    String uploadsDirPath = new File(UPLOADS_DIR).getCanonicalPath();
    File directory = new File(uploadsDirPath);
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Erreur lors de la création du dossier d'upload");
    }

    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File destinationFile = new File(directory, fileName);
    file.transferTo(destinationFile);

    return "/api/images/" + fileName;
  }
}
