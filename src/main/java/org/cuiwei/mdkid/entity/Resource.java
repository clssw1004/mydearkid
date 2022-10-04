package org.cuiwei.mdkid.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "resource")
public class Resource {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    UUID uid;

    /**
     * 文件大小
     */
    @Column(name = "file_length")
    Long fileLength;

    /**
     * 上传时间
     */
    LocalDateTime uploadTime;


}
