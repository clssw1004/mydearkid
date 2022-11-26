package org.cuiwei.mdkid.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.model.Photo;
import org.cuiwei.mdkid.service.PhotoService;
import org.cuiwei.mdkid.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RefreshPhotos {
    @Value("${config.path.photo}")
    String photoPath;

    @Autowired
    PhotoService photoService;

    @Scheduled(cron = "0 0 * * * ?")
    public void refresh() {
        File dir = new File(photoPath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        List<Photo> photos = photoService.listAll();
        Map<String, Photo> photoMap = new HashMap<>();
        if (CollUtil.isNotEmpty(photos)) {
            photoMap = photos.stream().collect(Collectors.toMap(Photo::getFileSha256, p -> p));
        }
        String[] children = dir.list();
        for (String f : children) {
            try {
                if (!ImageUtil.isSupportImg(f)) {
                    log.info("Not support file --> {}", f);
                    continue;
                }
                File file = new File(dir, f);
                String sha256 = DigestUtil.sha256Hex(file);
                if (file.isFile() && !photoMap.containsKey(sha256)) {
                    photoService.saveResource(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
