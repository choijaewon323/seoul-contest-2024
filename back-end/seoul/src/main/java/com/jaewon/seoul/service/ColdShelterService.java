package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.ColdShelterResponseDto;
import com.jaewon.seoul.util.LatLng;

import java.util.List;

public interface ColdShelterService {
    void update() throws Exception;
    List<ColdShelterResponseDto> getList(LatLng latLng);
    List<ColdShelterResponseDto> getNearThree(LatLng latLng);
}
