package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.HotShelterResponseDto;
import com.jaewon.seoul.entity.HotShelter;
import com.jaewon.seoul.util.LatLng;

import java.util.List;

public interface HotShelterService {
    void update() throws Exception;
    List<HotShelterResponseDto> getList(LatLng latLng);
    List<HotShelterResponseDto> getNearThree(LatLng latLng);
}
