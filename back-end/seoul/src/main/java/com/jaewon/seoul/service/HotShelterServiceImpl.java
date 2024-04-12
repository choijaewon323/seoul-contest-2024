package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.HotShelterResponseDto;
import com.jaewon.seoul.entity.HotShelter;
import com.jaewon.seoul.repository.HotShelterRepository;
import com.jaewon.seoul.util.LatLng;
import com.jaewon.seoul.util.Unit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class HotShelterServiceImpl implements HotShelterService {

    private final HotShelterRepository hotShelterRepository;
    private final ResourceLoader resourceLoader;

    public HotShelterServiceImpl(HotShelterRepository hotShelterRepository, ResourceLoader resourceLoader) {
        this.hotShelterRepository = hotShelterRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotShelterResponseDto> getList(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<HotShelter> hotShelters = hotShelterRepository.findByLatAndLngBetween(startLat, endLat, startLng, endLng);

        List<HotShelterResponseDto> results = new ArrayList<>();
        hotShelters.stream().forEach((e) -> {
            results.add(e.toResponseDto());
        });

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HotShelterResponseDto> getNearThree(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<HotShelter> hotShelters = hotShelterRepository.findByLatAndLngBetween(startLat, endLat, startLng, endLng);

        if (hotShelters.isEmpty()) {
            return new ArrayList<>();
        }

        hotShelters.sort((e1, e2) -> {
            double e1Dist = calculate(latLng, new LatLng(e1.getLatitude(), e1.getLongitude()));
            double e2Dist = calculate(latLng, new LatLng(e2.getLatitude(), e2.getLongitude()));

            if (e1Dist < e2Dist) {
                return -1;
            }
            return 1;
        });

        List<HotShelterResponseDto> results = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            results.add(hotShelters.get(i).toResponseDto());
        }

        return results;
    }

    @Override
    public void update() throws Exception {
        final String path = "classpath:static/hot-shelter.json";
        final File file = resourceLoader.getResource(path).getFile();
        final Reader reader = new FileReader(file);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

        JSONArray data = (JSONArray) jsonObject.get("DATA");

        for (int i = 0; i < data.size(); i++) {
            JSONObject element = (JSONObject) data.get(i);

            String name = (String) element.get("r_area_nm");
            String areaCode = (String) element.get("area_cd");
            String roadAddress = (String) element.get("r_detl_add");
            String oldAddress = (String) element.get("j_detl_add");
            Double latitude = (Double) element.get("la");
            Double longitude = (Double) element.get("lo");
            Double x = (Double) element.get("xx");
            Double y = (Double) element.get("yy");
            String start = (String) element.get("dt_start") ;
            String end = (String) element.get("dt_end");
            Integer airConditionerCnt = ((Long) element.get("cler2_cnt")).intValue();
            Integer fanCnt = ((Long) element.get("cler1_cnt")).intValue();
            String isUsed = (String) element.get("use_yn");
            String isHolidayOpen = (String) element.get("chk2_yn");

            hotShelterRepository.save(HotShelter.builder()
                            .name(name)
                            .areaCode(areaCode)
                            .roadAddress(roadAddress)
                            .oldAddress(oldAddress)
                            .latitude(latitude)
                            .longitude(longitude)
                            .x(x)
                            .y(y)
                            .start(start)
                            .end(end)
                            .airConditionerCnt(airConditionerCnt)
                            .fanCnt(fanCnt)
                            .isUsed(isUsed)
                            .isHolidayOpen(isHolidayOpen)
                            .build());
        }
    }

    private double calculate(LatLng src, LatLng dst) {
        double latDist = Math.abs(src.getLat() - dst.getLat());
        double lngDist = Math.abs(src.getLng() - dst.getLng());

        return Math.sqrt(latDist * latDist + lngDist * lngDist);
    }
}
