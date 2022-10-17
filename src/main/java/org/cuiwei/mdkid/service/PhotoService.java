package org.cuiwei.mdkid.service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.entity.Photo;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.util.ExifUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PhotoService {

    @Resource
    PhotoRepository photoRepository;

    public List<Photo> list()
    {
        List<Photo> photos = photoRepository.findAll();
        return photos;
    }

    public String saveResource(Photo photo) {
        photoRepository.save(photo);
        return photo.getUid();
    }

    public String saveResource(File img) throws ImageProcessingException, IOException {
        Photo photo = new Photo();
        photo.setFileLength(img.length());
        photo.setUploadTime(LocalDateTime.now());
        photo.setPath(img.getPath());
        photo.setFid(img.getPath());
        Metadata metadata = ImageMetadataReader.readMetadata(img);
        photo.setAddress(ExifUtil.getAddress(metadata));
        photo.setTakeTime(ExifUtil.getTakeTime(metadata));
        photoRepository.save(photo);
        return photo.getUid();

    }
}
