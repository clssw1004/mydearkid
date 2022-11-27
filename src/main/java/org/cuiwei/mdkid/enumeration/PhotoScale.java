package org.cuiwei.mdkid.enumeration;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

public enum PhotoScale {
    MINIMUM("minimum", 100f),
    MINI("mini", 2000f),
    MEDIUM("medium", 300f),
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
