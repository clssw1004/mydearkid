package org.cuiwei.mdkid;

import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.core.Response;
import org.cuiwei.mdkid.service.PhotoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

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

    @GetMapping("/origin/{fid}")
    public void originPhoto(@PathVariable("fid") String fid, HttpServletResponse response) throws IOException {
        File file = photoService.getOriginal(fid);
        OutputStream os = response.getOutputStream();
        FileUtil.writeToStream(file, os);
    }

    @GetMapping("/thumbnail/{fid}")
    public void previewPhoto(@PathVariable("fid") String fid, HttpServletResponse response) throws IOException {
        File file = photoService.getThumbnail(fid);
        if (file.exists()) {
//            float quality = 100000f / file.length();
//            log.info("{},{}", file.length(), quality);
//        BufferedImage image = ImageIO.read(file);
//        ImageIO.write(image, "webp", response.getOutputStream());
//            ImageUtil.toWebp(file, response.getOutputStream(), WebPWriteParam.LOSSY_COMPRESSION, quality);
//            ImgUtil.scale(new FileInputStream(file), response.getOutputStream(), quality);
//            ImageUtil.scale(file, response.getOutputStream());
            FileUtil.writeToStream(file, response.getOutputStream());
        }
    }
}
