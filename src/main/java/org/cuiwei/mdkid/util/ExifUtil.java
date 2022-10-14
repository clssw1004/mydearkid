package org.cuiwei.mdkid.util;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.gaode.GaoDeApi;
import org.cuiwei.mdkid.gaode.dto.AddressComponent;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Slf4j
public class ExifUtil {
    public static AddressComponent getAddress(Metadata metadata) {

        AddressComponent addressInfo = null;
        try {
            Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
            for (GpsDirectory gpsDirectory : gpsDirectories) {
                GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    log.info(geoLocation.toString());
                    addressInfo = GaoDeApi.getAddressByCoordinate(geoLocation.getLongitude(), geoLocation.getLatitude());
                    break;
                }
            }
            return addressInfo;
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime getTakeTime(Metadata metadata) {
        try {
            ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
            return LocalDateTimeUtil.of(date);
        } catch (Exception e) {
            return null;
        }
    }
}
