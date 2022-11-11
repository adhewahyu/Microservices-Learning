package com.dan.msmasterdata.repository;

import com.dan.msmasterdata.model.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String>, JpaSpecificationExecutor<Province> {
}
