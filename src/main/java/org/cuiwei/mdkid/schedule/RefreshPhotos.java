package org.cuiwei.mdkid.schedule;

import org.cuiwei.mdkid.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;

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
        String[] children = dir.list();
        for (String f : children) {
            try {
                File file = new File(dir, f);
                if (file.isFile() && !photoService.photoExist(file)) {
                    photoService.saveResource(file);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
