package com.jaewon.seoul.controller;

import com.jaewon.seoul.dto.HospitalResponseDto;
import com.jaewon.seoul.service.HospitalService;
import com.jaewon.seoul.util.LatLng;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/hospital")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HospitalApiController {

    private final HospitalService hospitalService;

    public HospitalApiController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @GetMapping("/{lat}/{lng}")
    public ResponseEntity<List<HospitalResponseDto>> getHospitals(@PathVariable Double lat, @PathVariable Double lng) {
        List<HospitalResponseDto> results = hospitalService.getList(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }

    @GetMapping("/near/{lat}/{lng}")
    public ResponseEntity<List<HospitalResponseDto>> getNearThree(@PathVariable Double lat, @PathVariable Double lng) {
        List<HospitalResponseDto> results = hospitalService.getNearThree(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }
}
