package com.jaewon.seoul.controller;

import com.jaewon.seoul.dto.ColdShelterResponseDto;
import com.jaewon.seoul.service.ColdShelterService;
import com.jaewon.seoul.util.LatLng;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/cold")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ColdShelterApiController {
    private final ColdShelterService coldShelterService;

    public ColdShelterApiController(ColdShelterService coldShelterService) {
        this.coldShelterService = coldShelterService;
    }

    @GetMapping("/{lat}/{lng}")
    public ResponseEntity<List<ColdShelterResponseDto>> getList(@PathVariable Double lat,
                                        @PathVariable Double lng) {
        List<ColdShelterResponseDto> results = coldShelterService.getList(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }

    @GetMapping("/near/{lat}/{lng}")
    public ResponseEntity<List<ColdShelterResponseDto>> getNearThree(@PathVariable Double lat, @PathVariable Double lng) {
        List<ColdShelterResponseDto> results = coldShelterService.getNearThree(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }
}
