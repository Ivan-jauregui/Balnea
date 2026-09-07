    package com.balneamdp.service;

    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.Arrays;
    import java.util.List;

    @Service
    public class ArchiveValidatorService {

        private static final List<String> PERMITTED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "webp");


        public void validateFile(MultipartFile archivo) {
            if (archivo == null || archivo.isEmpty()) {
                throw new IllegalArgumentException("El archivo no puede estar vacío");
            }

            String fileName = archivo.getOriginalFilename();
            if (fileName == null || !fileName.contains(".")) {
                throw new IllegalArgumentException("El archivo no tiene una extensión válida");
            }

            // Extrae la extensión del nombre del archivo (ejemplo: "balneario-12.png" -> "png")
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase().trim();

            if (!PERMITTED_EXTENSIONS.contains(extension)) {
                throw new IllegalArgumentException("Formato Inválido. Solo se aceptan extensiones JPG, JPEG, PNG y WEBP");
            }
        }
    }
