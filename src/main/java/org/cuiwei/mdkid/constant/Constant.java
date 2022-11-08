package org.cuiwei.mdkid.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Constant {
    public static final String GAODE_APPID = "bbeee6a186f33f60ad3670dc3f9e706b";

    public static List<String> SUPPORT_EXTENSIONS = new ArrayList<>();

    public static final String ALL_TYPE_EXTENSION = "*";

    @Value("${config.EXTENSIONS:*}")
    public void setSupportExtensions(String extensions) {
        SUPPORT_EXTENSIONS = Arrays.stream(extensions.split(",")).map(ext -> ext.toUpperCase()).toList();
    }


}
