package org.cuiwei.mdkid.util;

import cn.hutool.core.util.StrUtil;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import kong.unirest.JsonPatch;
import org.cuiwei.mdkid.constant.Constant;

import cn.hutool.core.text.StrFormatter;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.cuiwei.mdkid.gaode.response.AddressResponse;

@Slf4j
public class CoordinateUtil {
    /**
     * 将度分秒格式经纬度字符串转换为小数格式
     *
     * @param latlng
     * @return
     */
    public static double convertToDecimalByString(String latlng) {
        return convertToDecimalByString(latlng, "°", "'", "\"");
    }

    /**
     * 将度分秒格式经纬度字符串转换为小数格式
     *
     * @param latlng
     * @param d      度字符
     * @param f      分字符
     * @param m      秒字符
     * @return
     */
    public static double convertToDecimalByString(String latlng, String d, String f, String m) {
        double du = Double.parseDouble(latlng.substring(0, latlng.indexOf(d)));
        double fen = Double.parseDouble(latlng.substring(latlng.indexOf(d) + 1, latlng.indexOf(f)));
        double miao = Double.parseDouble(latlng.substring(latlng.indexOf(f) + 1, latlng.indexOf(m)));
        if (du < 0)
            return -(Math.abs(du) + (fen + (miao / 60)) / 60);
        return du + (fen + (miao / 60)) / 60;
    }
}
