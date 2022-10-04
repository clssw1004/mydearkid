package org.cuiwei.mdkid.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "resource")
@Getter
@Setter
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class ResFile {
    @Id
    @Column(name = "id", length = 32, nullable = false)
    @GeneratedValue(generator = "jpa-uuid")
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

    @Column(length = 32, nullable = false)
    String fid;

    @Column(length = 32, name = "event_id")
    String eventId;

    @Column(length = 32, columnDefinition = "text")
    String remark;

}
