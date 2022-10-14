package org.cuiwei.mdkid.gaode.dto;

import cn.hutool.core.text.StrFormatter;
import lombok.Data;

@Data
public class AddressComponent {
    String city;
    String province;
    String adcode;
    String district;
    String towncode;
    StreetNumber streetNumber;
    String country;
    String township;
    String formatted_address;

    public String simple() {
        return StrFormatter.format("{} {} {} {} {}", country, province, city, district, township);
    }

}
