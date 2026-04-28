package com.crusos.infrastructure.adapter.out.storage;

import com.crusos.domain.port.out.FileStoragePort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileSystemStorageAdapter implements FileStoragePort {

    private final Path fileStorageLocation;
    private final String baseUrl;

    public FileSystemStorageAdapter(@Value("${app.upload.dir:uploads}") String uploadDir, 
                                    @Value("${app.base.url:http://localhost:8080}") String baseUrl) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.baseUrl = baseUrl;

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio donde se guardarán los archivos subidos.", ex);
        }
    }

    @Override
    public String storeFile(MultipartFile file) {
        String originalName = StringUtils.cleanPath(file.getOriginalFilename());
        
        // Validación básica de tipos
        if (originalName == null || (!originalName.toLowerCase().endsWith(".png") && !originalName.toLowerCase().endsWith(".jpg") && !originalName.toLowerCase().endsWith(".jpeg"))) {
            throw new IllegalArgumentException("Solo se permiten archivos PNG o JPG");
        }

        try {
            // Renombrar archivo para evitar conflictos
            String fileName = UUID.randomUUID().toString() + "_" + originalName;
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return baseUrl + "/uploads/" + fileName;
        } catch (IOException ex) {
            throw new RuntimeException("No se pudo guardar el archivo " + originalName, ex);
        }
    }
}
