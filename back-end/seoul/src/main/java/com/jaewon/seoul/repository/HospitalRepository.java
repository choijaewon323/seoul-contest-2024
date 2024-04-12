package com.jaewon.seoul.repository;

import com.jaewon.seoul.entity.Hospital;
import com.jaewon.seoul.entity.HotShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    @Query("select s from Hospital s where s.latitude between :startLat and :endLat " +
            "and s.longitude between :startLng and :endLng")
    List<Hospital> findByLatAndLngBetween(@Param("startLat") double startLat, @Param("endLat") double endLat,
                                            @Param("startLng") double startLng, @Param("endLng") double endLng);
}
