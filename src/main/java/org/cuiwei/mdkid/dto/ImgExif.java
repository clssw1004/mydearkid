package org.cuiwei.mdkid.dto;

import cn.hutool.core.date.DateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class ImgExif {

    String simpleAddr;
    String address;
    Date camDate;
}
