package org.cuiwei.mdkid.gaode;

import cn.hutool.core.text.StrFormatter;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.constant.Constant;
import org.cuiwei.mdkid.gaode.dto.AddressComponent;
import org.cuiwei.mdkid.gaode.response.AddressResponse;
import org.cuiwei.mdkid.util.CoordinateUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class GaoDeApi {

    /**
     * 根据经纬度信息获取地址
     *
     * @param lng
     * @param lat
     * @return
     */
    public static AddressComponent getAddressByCoordinate(double lng, double lat) {
        String location = StrFormatter.format("{},{}", lng, lat);
        String url = StrFormatter.format("https://restapi.amap.com/v3/geocode/regeo?key={}&location={}",
                Constant.GAODE_APPID, location);
        HttpResponse<AddressResponse> res = Unirest.get(url).asObject(AddressResponse.class);

        if (res.isSuccess()) {
            AddressResponse addressResponse = res.getBody();
            if ("1".equals(addressResponse.getStatus()) && addressResponse.getRegeocode() != null && addressResponse.getRegeocode().getAddressComponent() != null) {
                return addressResponse.getRegeocode().getAddressComponent();
            }
        }
        return null;
    }

    public static AddressComponent gerAddressFromImages(File imgFile) {
        AddressComponent positionInfo = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imgFile);
            Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
            for (GpsDirectory gpsDirectory : gpsDirectories) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    log.info(geoLocation.toString());
                    positionInfo = getAddressByCoordinate(geoLocation.getLongitude(), geoLocation.getLatitude());
                    break;
                }
            }
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
        return positionInfo;
    }

    public static void main(String[] args) {
        log.info("{}", gerAddressFromImages(new File("F:\\cuiwei\\Images\\00b90751258f8aba093a1119e5266ceda1af3ac7.jpg")).simple());
    }
}
