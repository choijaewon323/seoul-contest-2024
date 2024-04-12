package com.jaewon.seoul.controller;

import com.jaewon.seoul.service.ColdShelterService;
import com.jaewon.seoul.service.HospitalService;
import com.jaewon.seoul.service.HotShelterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DataUpdateApiController {

    private final HotShelterService hotShelterService;
    private final ColdShelterService coldShelterService;
    private final HospitalService hospitalService;

    private static final String SUCCESS = "SUCCESS";

    public DataUpdateApiController(HotShelterService hotShelterService,
                                   ColdShelterService coldShelterService, HospitalService hospitalService) {
        this.hotShelterService = hotShelterService;
        this.coldShelterService = coldShelterService;
        this.hospitalService = hospitalService;
    }

    @PostMapping("/api/v1/hot/json")
    public ResponseEntity<String> updateHot() throws Exception {
        hotShelterService.update();

        return ResponseEntity.ok(SUCCESS);
    }

    @PostMapping("/api/v1/cold/json")
    public ResponseEntity<String> updateCold() throws Exception {
        coldShelterService.update();

        return ResponseEntity.ok(SUCCESS);
    }

    @PostMapping("/api/v1/hospital/json")
    public ResponseEntity<String> updateHospital() throws Exception {
        hospitalService.update();

        return ResponseEntity.ok(SUCCESS);
    }
}
