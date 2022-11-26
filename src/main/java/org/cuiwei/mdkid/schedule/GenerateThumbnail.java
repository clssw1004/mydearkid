package org.cuiwei.mdkid.schedule;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.model.Photo;
import org.cuiwei.mdkid.service.PhotoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class GenerateThumbnail {


    @Resource
    PhotoService photoService;

    @Scheduled(cron = "0 0 * * * ?")
    public void generateThumbnail() throws IOException {
        List<Photo> photos = photoService.listAll();
        if (CollectionUtil.isEmpty(photos)) {
            return;
        }
        for (var photo : photos) {
            try {
                photoService.getThumbnail(photo.getFid());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
