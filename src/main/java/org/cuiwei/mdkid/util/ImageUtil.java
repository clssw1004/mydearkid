package org.cuiwei.mdkid.util;

import cn.hutool.core.img.ImgUtil;
import com.luciad.imageio.webp.WebPWriteParam;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ImageUtil {
    private ImageUtil() {

    }

    public static void toWebp(File imgFile, OutputStream outputStream) throws IOException {
        toWebp(imgFile, outputStream, WebPWriteParam.LOSSLESS_COMPRESSION, 1.0f);
    }


    public static void toWebp(File imgFile, OutputStream outputStream, int pWriteParam, float compressQuality) throws IOException {
        BufferedImage image = ImageIO.read(imgFile);

        ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();

        WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
        //Notify encoder to consider WebPWriteParams
        writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        //Set lossless compression
        writeParam.setCompressionType(writeParam.getCompressionTypes()[pWriteParam]);
        writeParam.setCompressionQuality(compressQuality);

        writer.setOutput(ImageIO.createImageOutputStream(outputStream));
        writer.write(null, new IIOImage(image, null, null), writeParam);
    }

    public static void scale(File file, OutputStream os) throws IOException {
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        float scale = 1f;
        if(width>height)
        {
            scale = 200f/width;
        }else {
            scale = 200f/height;
        }
        ImgUtil.scale(new FileInputStream(file),os, scale);
    }
}
