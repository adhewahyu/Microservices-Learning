package com.dan.msnotification.repository;

import com.dan.msnotification.model.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, String>, JpaSpecificationExecutor<EmailTemplate> {

    @Query(value = "select * from email_templates where type = :type and deleted = false", nativeQuery = true)
    Optional<EmailTemplate> findByType(@Param("type") String type);

    @Query(value = "select * from email_templates where deleted = false", nativeQuery = true)
    List<EmailTemplate> findActiveEmailTemplate();

}
