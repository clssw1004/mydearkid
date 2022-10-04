package org.cuiwei.mdkid.repository;

import org.cuiwei.mdkid.entity.ResFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResFileRepository extends JpaRepository<ResFile,String> {
}
