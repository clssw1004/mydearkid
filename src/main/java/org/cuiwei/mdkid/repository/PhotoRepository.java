package org.cuiwei.mdkid.repository;

import org.cuiwei.mdkid.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo,String> {
}
