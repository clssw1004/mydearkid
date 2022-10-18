package org.cuiwei.mdkid.schedule;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.StrFormatter;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.entity.Photo;
import org.cuiwei.mdkid.service.PhotoService;
import org.cuiwei.mdkid.util.ImageUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Slf4j
public class GenerateThumbnail {


    @Resource
    PhotoService photoService;

    @Scheduled(cron = "0 22 * * * ?")
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
