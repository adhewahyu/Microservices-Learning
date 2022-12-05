package com.dan.msmasterdata.repository;

import com.dan.msmasterdata.model.entity.City;
import com.dan.msmasterdata.model.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {

    @Query(value = "select count(id) from cities where city_code = :cityCode and is_deleted = false",nativeQuery = true)
    Integer countCityByCityCode(@Param("cityCode") String cityCode);

    @Query(value = "select count(id) from cities where city_name = :cityName and is_deleted = false",nativeQuery = true)
    Integer countCityByCityName(@Param("cityName") String cityName);

    @Query(value = "select * from cities where is_deleted = false and is_active = true", nativeQuery = true)
    List<City> findAllActiveCity();

}
