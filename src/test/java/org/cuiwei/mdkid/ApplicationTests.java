package org.cuiwei.mdkid;

import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.repository.PhotoRepository;
import org.cuiwei.mdkid.schedule.RefreshPhotos;
import org.cuiwei.mdkid.service.PhotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    PhotoService photoService;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    RefreshPhotos refreshPhotos;

    @Test
    void rp() {
        refreshPhotos.refresh();
    }

}
