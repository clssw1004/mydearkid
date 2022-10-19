package org.cuiwei.mdkid.schedule;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.crypto.digest.DigestUtil;
import org.cuiwei.mdkid.entity.Photo;
import org.cuiwei.mdkid.service.PhotoService;
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
public class RefreshPhotos {
    @Value("${config.path.photo}")
    String photoPath;

    @Autowired
    PhotoService photoService;

    @Scheduled(cron = "0 0/10 * * * ?")
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
