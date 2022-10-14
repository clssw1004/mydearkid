package org.cuiwei.mdkid.util;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifThumbnailDirectory;
import com.drew.metadata.exif.GpsDirectory;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@Slf4j
public class ImageUtil {
    public static String gerAddressFromImages(File imgFile) {
        String positionInfo = null;
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imgFile);
            Collection<GpsDirectory> gpsDirectories = metadata.getDirectoriesOfType(GpsDirectory.class);
            for (GpsDirectory gpsDirectory : gpsDirectories) {
                  GeoLocation geoLocation = gpsDirectory.getGeoLocation();
                if (geoLocation != null && !geoLocation.isZero()) {
                    log.info(geoLocation.toString());
                }
            }
        } catch (ImageProcessingException | IOException e) {
            throw new RuntimeException(e);
        }
        return positionInfo;
    }

    public static void main(String[] args) {
        gerAddressFromImages(new File("F:\\cuiwei\\Images\\00b90751258f8aba093a1119e5266ceda1af3ac7.jpg"));
    }
}
