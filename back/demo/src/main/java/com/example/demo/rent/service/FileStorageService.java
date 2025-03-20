package com.example.demo.rent.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class FileStorageService {

  private static final String UPLOADS_DIR = "./uploads/images/";

  /**
   * Saves an image file to the file system and returns the URL path where it is accessible.
   *
   * @param file the image file to be saved. It must not be empty and must have a content type starting with "image/".
   * @return the URL path of the saved file in the format "/api/images/{filename}".
   * @throws IOException if an error occurs while saving the file or creating the upload directory.
   * @throws IllegalArgumentException if the file is empty or if the file's content type does not start with "image/".
   */
  public String saveFile(MultipartFile file) throws IOException {
    // check if file is empty
    if (file.isEmpty()) {
      throw new IllegalArgumentException("Le fichier est vide");
    }
    // check if file is image
    if (!file.getContentType().startsWith("image/")) {
      throw new IllegalArgumentException("Le fichier n'est pas une image");
    }

    // get directory to save file, create folder if needed
    String uploadsDirPath = new File(UPLOADS_DIR).getCanonicalPath();
    File directory = new File(uploadsDirPath);
    if (!directory.exists() && !directory.mkdirs()) {
      throw new IOException("Erreur lors de la création du dossier d'upload");
    }

    // create filename and save it
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
    File destinationFile = new File(directory, fileName);
    file.transferTo(destinationFile);

    // return path where file will be served
    return "/api/images/" + fileName;
  }
}
