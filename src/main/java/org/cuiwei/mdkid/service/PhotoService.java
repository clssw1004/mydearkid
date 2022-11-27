package org.cuiwei.mdkid.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.dto.PhotoGroup;
import org.cuiwei.mdkid.dto.PhotoYearDistribute;
import org.cuiwei.mdkid.enumeration.PhotoScale;
import org.cuiwei.mdkid.model.Photo;
import org.cuiwei.mdkid.model.QPhoto;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.util.ExifUtil;
import org.cuiwei.mdkid.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class PhotoService {

    @Autowired
    JPAQueryFactory queryFactory;

    @Value("${config.path.thumbnail}")
    String thumbnailPath;

    @Resource
    PhotoRepository photoRepository;

    public List<PhotoGroup> listAllGroupBy(String groupBy, Integer year) {
        final String format = StrUtil.isBlank(groupBy) ? "yyyy-mm" : groupBy;
        QPhoto photo = QPhoto.photo;
        List<Photo> photos = queryFactory.selectFrom(photo)
                .where(photo.takeTime.isNotNull().and(photo.takeTime.year().eq(year)))
                .limit(200).offset(1)
                .orderBy(photo.takeTime.desc())
                .fetch();
        Map<String, List<Photo>> groupPhotos = new LinkedHashMap<>();
        if (CollUtil.isNotEmpty(photos)) {
            photos.forEach(p -> {
                String dt;
                if (p.getTakeTime() != null) {
                    dt = p.getTakeTime().format(DateTimeFormatter.ofPattern(format));
                } else {
                    dt = "#";
                }
                if (!groupPhotos.containsKey(dt)) {
                    groupPhotos.put(dt, new ArrayList<>());
                }
                groupPhotos.get(dt).add(p);
            });
        }
        return groupPhotos.entrySet().stream().map(e -> new PhotoGroup(e.getKey(), e.getValue())).toList();
    }

    public List<PhotoYearDistribute> getYears() {
        QPhoto photo = QPhoto.photo;
        List<Tuple> years = queryFactory.select(photo.takeTime.year(), photo.count()).from(photo).orderBy(photo.takeTime.year().asc()).groupBy(photo.takeTime.year()).fetch();
        List<PhotoYearDistribute> result = new ArrayList<>();
        if (years.size() > 0) {
            for (Tuple tuple : years) {
                Integer year = tuple.get(0, Integer.class);
                Long count = tuple.get(1, Long.class);
                if (year == null) {
                    result.add(new PhotoYearDistribute("#", count));
                } else {
                    result.add(new PhotoYearDistribute(year.toString(), count));
                }
            }
        }
        return result;
    }

    public List<Photo> listAll() {
        QPhoto photo = QPhoto.photo;
        List<Photo> photos = queryFactory.selectFrom(photo)
                .orderBy(photo.takeTime.desc())
                .fetch();
        return photos;
    }

    public Page<Photo> list() {
        QPhoto photo = QPhoto.photo;
        Pageable pageable = PageRequest.of(1, 50);
        List<Photo> photos = queryFactory.selectFrom(photo)
                .where(photo.extension.ne("HEIC").and(photo.takeTime.isNotNull())).orderBy(photo.takeTime.desc()).offset(pageable.getOffset()).limit(pageable.getPageSize())
                .fetch();
        return new PageImpl<>(photos, pageable, 1000);
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
//        photo.setAddress(ExifUtil.getAddress(metadata));
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

    public File getThumbnail(String fid, PhotoScale scale) throws IOException {
        if (!FileUtil.exist(thumbnailPath)) {
            FileUtil.mkdir(thumbnailPath);
        }
        File photo = getOriginal(fid);
        if (FileUtil.exist(photo)) {
            File destFile = new File(StrFormatter.format("{}{}{}{}{}.jpg", thumbnailPath, File.separator, scale.getScale(), File.separator, fid));
            if (!FileUtil.exist(destFile)) {
                log.info("scale {} -> {}", photo.getPath(), destFile.getPath());
                ImageUtil.scale(
                        photo,
                        scale.getPx(),
                        FileUtil.getOutputStream(destFile));
            }
            return destFile;
        } else {
            return null;
        }
    }
}
