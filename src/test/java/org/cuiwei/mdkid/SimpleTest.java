package org.cuiwei.mdkid;

import cn.hutool.core.text.StrFormatter;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.util.CoordinateUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

@Slf4j
public class SimpleTest {

    @Test
    void getMetaData() throws ImageProcessingException, IOException {
        File jpegFile = new File("/home/cuiwei/Pictures/2022-10-04 161149.heic");
        Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }
    }

    @Test
    void getPosition() {
        String key = "bbeee6a186f33f60ad3670dc3f9e706b";
//        String location = "118.4333,31.375162";
//
//        String url1 = StrFormatter.format("https://restapi.amap.com/v3/assistant/coordinate/convert?key={}&locations={}&coordsys=gps",
//                key, location);
//        HttpResponse<JsonNode> res1 = Unirest.get(url1).asJson();
//        if (res1.isSuccess()) {
//            log.info("{}", res1.getBody().toPrettyString());
//        }

        String location2 = StrFormatter.format("{},{}",
                CoordinateUtil.convertToDecimalByString("118° 43' 3.3\""),
                CoordinateUtil.convertToDecimalByString("31° 37' 51.62\""));
        String url2 = StrFormatter.format("https://restapi.amap.com/v3/geocode/regeo?key={}&location={}",
                key,
                location2);
        HttpResponse<JsonNode> res2 = Unirest.get(url2).asJson();
        if (res2.isSuccess()) {
            log.info("{}", res2.getBody().toPrettyString());
        }
    }
}
