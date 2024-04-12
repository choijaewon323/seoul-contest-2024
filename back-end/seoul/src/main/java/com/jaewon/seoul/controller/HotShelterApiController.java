package com.jaewon.seoul.controller;

import com.jaewon.seoul.dto.HotShelterResponseDto;
import com.jaewon.seoul.service.HotShelterService;
import com.jaewon.seoul.util.LatLng;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/hot")
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HotShelterApiController {
    private final HotShelterService hotShelterService;

    public HotShelterApiController(HotShelterService hotShelterService) {
        this.hotShelterService = hotShelterService;
    }

    @GetMapping("/{lat}/{lng}")
    public ResponseEntity<List<HotShelterResponseDto>> getShelterList(@PathVariable Double lat, @PathVariable Double lng) throws Exception {
        List<HotShelterResponseDto> results = hotShelterService.getList(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }

    @GetMapping("/near/{lat}/{lng}")
    public ResponseEntity<List<HotShelterResponseDto>> getNearThree(@PathVariable Double lat, @PathVariable Double lng) {
        List<HotShelterResponseDto> results = hotShelterService.getNearThree(new LatLng(lat, lng));

        return ResponseEntity.ok(results);
    }
}
