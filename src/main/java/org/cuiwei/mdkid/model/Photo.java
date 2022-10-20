package org.cuiwei.mdkid.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.cuiwei.mdkid.gaode.dto.AddressComponent;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "photo")
@Getter
@Setter
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class Photo {
    @Id
    @Column(name = "uid",length = 32)
    String uid;

    /**
     * 文件大小
     */
    @Column(name = "file_length", nullable = false)
    Long fileLength;

    @Column(length = 64)
    String extension;

    @Column(length = 255)
    String originName;

    /**
     * 上传时间
     */
    @Column(name = "upload_time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime uploadTime;

    @Column(name="file_sha256")
    String fileSha256;

    @Column(length = 512, nullable = false)
    String fid;

    @Column(length = 32, name = "event_id")
    String eventId;

    @Column(columnDefinition = "text")
    String remark;

    @Column(columnDefinition = "text")
    String path;
    /**
     * 拍摄时间
     */
    @Column(name = "take_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDateTime takeTime;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    AddressComponent address;

}
