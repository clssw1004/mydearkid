package org.cuiwei.mdkid.controller;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.core.Response;
import org.cuiwei.mdkid.dto.PhotoRequest;
import org.cuiwei.mdkid.enumeration.PhotoScale;
import org.cuiwei.mdkid.service.PhotoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/photo")
@Slf4j
public class PhotoController {

    @Resource
    PhotoService photoService;

    @GetMapping("/")
    public Response list() {
        return Response.builder().code(0).message("ok").data(photoService.list()).build();
    }

    @PostMapping("/groupWith")
    public Response groupWith(@RequestBody PhotoRequest request) {
        if (request.getY() == null) {
            request.setY(LocalDateTime.now().getYear() + "");
        }
        return Response.builder().code(0).message("ok").data(photoService.listAllGroupBy(request)).build();
    }

    @GetMapping("/years")
    public Response getYears() {
        return Response.builder().code(0).message("ok").data(photoService.getYears()).build();
    }


    @GetMapping("/origin/{fid}")
    public void originPhoto(@PathVariable("fid") String fid, HttpServletResponse response) throws IOException {
        File file = photoService.getOriginal(fid);
        OutputStream os = response.getOutputStream();
        FileUtil.writeToStream(file, os);
    }

    @GetMapping("/{scale}/{fid}")
    public void previewPhoto(@PathVariable("fid") String fid, @PathVariable("scale") PhotoScale scale, HttpServletResponse response) throws IOException {
        File file = photoService.getThumbnail(fid, scale);
        if (file.exists()) {
            FileUtil.writeToStream(file, response.getOutputStream());
        }
    }
}
