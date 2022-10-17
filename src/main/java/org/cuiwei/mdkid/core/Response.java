package org.cuiwei.mdkid.core;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Builder
public class Response {
    int code;
    String message;
    Object data;
}
