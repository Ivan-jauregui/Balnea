    package com.balneamdp.service;

    import org.springframework.stereotype.Service;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.Arrays;
    import java.util.List;

    @Service
    public class ArchiveValidatorService {

        private static final List<String> permittedType= Arrays.asList(
                "image/jpeg",
                "image/jpg",
                "image/png",
                "image/webp"
        );


        public void valditeFile(MultipartFile archivo) throws IllegalAccessException {
            if(archivo==null || archivo.isEmpty()){
                throw new IllegalAccessException("El archivo no puede estar vacio");
            }

            String contentType=archivo.getContentType();
            if(contentType==null || !permittedType.contains(contentType)){
                throw new IllegalAccessException("Formato Invalido. Solo se aceptan formatos JPEG,PNG Y WEBP");
            }
        }
    }
