package org.cuiwei.mdkid;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.entity.Photo;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;
   @Test
    void updateUuid()
    {
        List<Photo> photoList = photoRepository.findAll();
        photoList.stream().forEach(p->{
            p.setFid(IdUtil.simpleUUID());
        });
        photoRepository.saveAll(photoList);
    }

    @Test
    void addResFile() {
        File dir = new File("F:\\cuiwei\\Images");
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (String f : children) {
                try {
                    File file = new File(dir, f);
                    if (file.isFile()) {
                        photoService.saveResource(file);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
