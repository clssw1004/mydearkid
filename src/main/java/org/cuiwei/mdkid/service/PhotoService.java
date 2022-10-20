package org.cuiwei.mdkid.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.model.Photo;
import org.cuiwei.mdkid.model.QPhoto;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.util.ExifUtil;
import org.cuiwei.mdkid.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    JPAQueryFactory queryFactory;

    @Value("${config.path.thumbnail}")
    String thumbnailPath;

    @Resource
    PhotoRepository photoRepository;

    public List<Photo> listAll() {
        return photoRepository.findAll();
    }

    public Page<Photo> list() {
        QPhoto photo = QPhoto.photo;
//        queryFactory.select(photo).where()
        Page<Photo> photos = photoRepository.findAll(PageRequest.of(1, 60));
        return photos;
    }

    public boolean photoExist(File file) {
        return file.exists() && photoRepository.existsByFileSha256(DigestUtil.sha256Hex(file));
    }


    public String saveResource(Photo photo) {
        photoRepository.save(photo);
        return photo.getUid();
    }

    public String saveResource(File img) throws ImageProcessingException, IOException {
        Photo photo = new Photo();
        photo.setUid(IdUtil.simpleUUID());
        photo.setFileLength(img.length());
        photo.setUploadTime(LocalDateTime.now());
        photo.setPath(img.getPath());
        photo.setFid(IdUtil.simpleUUID());
        photo.setOriginName(img.getName());
        photo.setFileSha256(DigestUtil.sha256Hex(img));
        photo.setExtension(FileUtil.extName(img));
        Metadata metadata = ImageMetadataReader.readMetadata(img);
        photo.setAddress(ExifUtil.getAddress(metadata));
        photo.setTakeTime(ExifUtil.getTakeTime(metadata));
        photoRepository.save(photo);
        return photo.getUid();
    }

    public File getOriginal(String fid) throws IOException {
        Optional<Photo> optionalPhoto = photoRepository.findByFid(fid);
        if (optionalPhoto.isPresent()) {
            Photo photo = optionalPhoto.get();
            return new File(photo.getPath());
        }
        throw new RuntimeException("文件不存在");
    }

    public File getThumbnail(String fid) throws IOException {
        if (!FileUtil.exist(thumbnailPath)) {
            FileUtil.mkdir(thumbnailPath);
        }
        File photo = getOriginal(fid);
        File destFile = new File(StrFormatter.format("{}{}{}.jpg", thumbnailPath, File.separator, fid));
        if (!FileUtil.exist(destFile)) {
            log.info("scale {} -> {}", photo.getPath(), destFile);
            ImageUtil.scale(
                    photo,
                    FileUtil.getOutputStream(destFile));
        }
        return destFile;
    }
}
