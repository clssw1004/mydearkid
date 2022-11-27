package org.cuiwei.mdkid.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum PhotoScale {
    MINIMUM("minimum", 50f),
    MINI("mini", 150f),
    MEDIUM("medium", 250f),
    LARGE("large", 400f),
    LARGEST("largest", 500f);
    @Getter
    final float px;
    @JsonValue
    @Getter
    final String scale;

    PhotoScale(String scale, float px) {
        this.px = px;
        this.scale = scale;
    }
}
