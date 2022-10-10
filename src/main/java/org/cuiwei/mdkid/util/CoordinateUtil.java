package org.cuiwei.mdkid.util;

import org.cuiwei.mdkid.constant.Constant;

import cn.hutool.core.text.StrFormatter;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;

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

    /**
     * 根据经纬度信息获取地址
     * 
     * @param lng
     * @param lat
     * @return
     */
    public static String getAddressByCoordinate(double lng, double lat) {
        String location = StrFormatter.format("{},{}", lng, lat);
        String url = StrFormatter.format("https://restapi.amap.com/v3/geocode/regeo?key={}&location={}",
                Constant.GAODE_APPID, location);
        HttpResponse<JsonNode> res = Unirest.get(url).asJson();
        if (res.isSuccess()) {
            log.info("{}", res.getBody().toPrettyString());
        } else {
            return null;
        }
        return url;
    }
}
