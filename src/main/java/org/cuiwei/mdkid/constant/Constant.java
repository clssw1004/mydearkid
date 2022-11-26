package org.cuiwei.mdkid.constant;

import cn.hutool.core.io.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class Constant {
    public static  String GAODE_APPID;

    public static List<String> SUPPORT_EXTENSIONS = new ArrayList<>();

    public static final String ALL_TYPE_EXTENSION = "*";

    @Value("${config.path.gaodekey}")
    public void setGaodeAppid(String gaodekey) {
        GAODE_APPID = FileUtil.readUtf8String(gaodekey);
    }

    @Value("${config.EXTENSIONS:*}")
    public void setSupportExtensions(String extensions) {
        SUPPORT_EXTENSIONS = Arrays.stream(extensions.split(",")).map(ext -> ext.toUpperCase()).toList();
    }




}
