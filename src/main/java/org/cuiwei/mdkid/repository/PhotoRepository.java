package org.cuiwei.mdkid.repository;

import org.cuiwei.mdkid.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, String> {
    Optional<Photo> findByFid(String fid);
    boolean existsByFileSha256(String sha256);
}
