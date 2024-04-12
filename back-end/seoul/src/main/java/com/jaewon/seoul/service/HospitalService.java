package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.HospitalResponseDto;
import com.jaewon.seoul.util.LatLng;

import java.util.List;

public interface HospitalService {
    void update() throws Exception;
    List<HospitalResponseDto> getList(LatLng latLng);
    List<HospitalResponseDto> getNearThree(LatLng latLng);
}
