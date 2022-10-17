package org.cuiwei.mdkid;

import org.cuiwei.mdkid.core.Response;
import org.cuiwei.mdkid.service.PhotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Resource
    PhotoService photoService;

    @GetMapping("/")
    public Response list() {
        return Response.builder().code(0).message("ok").data(photoService.list()).build();
    }
}
