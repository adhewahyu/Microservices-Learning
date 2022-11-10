package com.dan.msaudit.repository;

import com.dan.msaudit.model.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Repository
public interface AuditRepository extends JpaRepository<Audit, String>, JpaSpecificationExecutor<Audit> {

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM AUDITS WHERE CREATED_DATE <  (CURRENT_DATE - interval :interval)", nativeQuery = true)
    void doHouskeepAudit(@Param("interval") String interval);

}
