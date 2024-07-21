package com.raguzf.roommatch.photo;
import org.springframework.stereotype.Service;

@Service
public class PhotoMapper {
    
    public static Photo toPhoto(PhotoDTO photoDTO) {
        if(photoDTO == null) {
            return null;
        }

        Photo photo = Photo.builder()
                            .photoUrl(photoDTO.photoUrl())
                            .build();
        
        return photo;                    

    }
}
