package org.cuiwei.mdkid.service;

import org.cuiwei.mdkid.entity.ResFile;
import org.cuiwei.mdkid.repository.ResFileRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResFileService {

    @Resource
    ResFileRepository resFileRepository;

    public String saveResource(ResFile resFile) {
        resFileRepository.save(resFile);
        return resFile.getUid().toString();
    }
}
