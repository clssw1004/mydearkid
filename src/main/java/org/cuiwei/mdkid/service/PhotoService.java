package org.cuiwei.mdkid.service;

import cn.hutool.core.io.FileUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.entity.Photo;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.util.ExifUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PhotoService {

    @Resource
    PhotoRepository photoRepository;

    public Page<Photo> list() {
        Page<Photo> photos = photoRepository.findAll(PageRequest.of(1, 60));
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

    public void getPhoto(String fid, HttpServletResponse response) throws IOException {
        Optional<Photo> optionalPhoto = photoRepository.findByFid(fid);
        if(optionalPhoto.isPresent())
        {
            Photo photo = optionalPhoto.get();
            File file = new File(photo.getFid());
            OutputStream os = response.getOutputStream();
            FileUtil.writeToStream(file, os);
        }
    }
}
