package org.cuiwei.mdkid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cuiwei.mdkid.model.Photo;

import java.util.List;

@Data
@AllArgsConstructor
public class PhotoGroup {
    String date;
    List<Photo> photos;
}
