package org.cuiwei.mdkid.entity;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.Getter;
import lombok.Setter;
import org.cuiwei.mdkid.gaode.dto.AddressComponent;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "photo")
@Getter
@Setter
@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
@TypeDef(
        name = "json",
        typeClass = JsonType.class
)
public class Photo {
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator = "uuid2")
    String uid;

    /**
     * 文件大小
     */
    @Column(name = "file_length", nullable = false)
    Long fileLength;

    /**
     * 上传时间
     */
    @Column(name = "upload_time", nullable = false)
    LocalDateTime uploadTime;

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
    LocalDateTime takeTime;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    AddressComponent address;

}
