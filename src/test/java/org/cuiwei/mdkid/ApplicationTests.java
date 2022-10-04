package org.cuiwei.mdkid;

import cn.hutool.core.util.IdUtil;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.entity.ResFile;
import org.cuiwei.mdkid.service.ResFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    ResFileService resFileService;

    @Test
    void addResFile() {
        ResFile file = new ResFile();
        file.setFileLength(10245L);
        file.setRemark("sasasa");
        file.setUploadTime(LocalDateTime.now());
        file.setFid(IdUtil.fastSimpleUUID());
        resFileService.saveResource(file);
    }



}
