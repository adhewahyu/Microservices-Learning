package com.dan.msmasterdata.repository;

import com.dan.msmasterdata.model.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, String>, JpaSpecificationExecutor<Province> {

    @Query(value = "select count(id) from provinces where province_code = :provinceCode and is_deleted = false",nativeQuery = true)
    Integer countProvinceByProvinceCode(@Param("provinceCode") String provinceCode);

    @Query(value = "select count(id) from provinces where province_name = :provinceName and is_deleted = false",nativeQuery = true)
    Integer countProvinceByProvinceName(@Param("provinceName") String provinceName);

    @Query(value = "select * from provinces where is_deleted = false and is_active = true", nativeQuery = true)
    List<Province> findAllActiveProvince();

}
