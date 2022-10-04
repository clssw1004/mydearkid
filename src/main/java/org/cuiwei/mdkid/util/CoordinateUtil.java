package org.cuiwei.mdkid.util;

public class CoordinateUtil {
    //以字符串形式输入经纬度的转换
    public static double convertToDecimalByString(String latlng){

        double du=Double.parseDouble(latlng.substring(0, latlng.indexOf("°")));
        double fen=Double.parseDouble(latlng.substring(latlng.indexOf("°")+1, latlng.indexOf("'")));
        double miao=Double.parseDouble(latlng.substring(latlng.indexOf("'")+1, latlng.indexOf("\"")));
        if(du<0)
            return -(Math.abs(du)+(fen+(miao/60))/60);
        return du+(fen+(miao/60))/60;

    }
}
