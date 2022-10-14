package org.cuiwei.mdkid.gaode.response;

import lombok.Data;
import org.cuiwei.mdkid.gaode.dto.Regeocode;

@Data
public class AddressResponse {
    String status;
    String info;
    String infocode;
    Regeocode regeocode;
}
