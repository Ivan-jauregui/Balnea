package com.balneamdp.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryService {
    private Cloudinary cloudinary;

        public Map<String,Object> uploadImage(MultipartFile archive,String folder) throws IOException {
            return cloudinary.uploader().upload(archive.getBytes(), ObjectUtils.asMap(
                    "folder",folder,
                    "resource_type","image"
            ));
        }

    public Map<String,Object> deleteImage(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
    }
}
