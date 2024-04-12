package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.ColdShelterResponseDto;
import com.jaewon.seoul.dto.HotShelterResponseDto;
import com.jaewon.seoul.entity.ColdShelter;
import com.jaewon.seoul.entity.HotShelter;
import com.jaewon.seoul.repository.ColdShelterRepository;
import com.jaewon.seoul.util.CoordConverter;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ColdShelterServiceImpl implements ColdShelterService {

    private final ColdShelterRepository coldShelterRepository;
    private final ResourceLoader resourceLoader;
    private final CoordConverter coordConverter;

    public ColdShelterServiceImpl(ColdShelterRepository coldShelterRepository,
                                  ResourceLoader resourceLoader, CoordConverter coordConverter) {
        this.coldShelterRepository = coldShelterRepository;
        this.resourceLoader = resourceLoader;
        this.coordConverter = coordConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColdShelterResponseDto> getList(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<ColdShelter> coldShelters = coldShelterRepository.findByLatAndLngBetween(startLat, endLat, startLng, endLng);

        List<ColdShelterResponseDto> results = new ArrayList<>();
        coldShelters.stream().forEach((e) -> {
            results.add(e.toResponseDto());
        });

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColdShelterResponseDto> getNearThree(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<ColdShelter> coldShelters = coldShelterRepository.findByLatAndLngBetween(startLat, endLat, startLng, endLng);

        if (coldShelters.isEmpty()) {
            return new ArrayList<>();
        }

        coldShelters.sort((e1, e2) -> {
            double e1Dist = calculate(latLng, new LatLng(e1.getLatitude(), e1.getLongitude()));
            double e2Dist = calculate(latLng, new LatLng(e2.getLatitude(), e2.getLongitude()));

            if (e1Dist < e2Dist) {
                return -1;
            }
            return 1;
        });

        List<ColdShelterResponseDto> results = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            results.add(coldShelters.get(i).toResponseDto());
        }

        return results;
    }

    @Override
    public void update() throws Exception {
        final String path = "classpath:static/cold-shelter.json";
        final File file = resourceLoader.getResource(path).getFile();
        final Reader reader = new FileReader(file);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray data = (JSONArray) jsonObject.get("DATA");

        for (int i = 0; i < data.size(); i++) {
            JSONObject element = (JSONObject) data.get(i);

            String name = (String) element.get("r_area_nm");
            String roadAddress = (String) element.get("r_detl_add");
            String startWeekday = (String) element.get("c_strt");
            String endWeekday = (String) element.get("c_end");
            String startHoliday = (String) element.get("c_strt_wd");
            String endHoliday = (String) element.get("c_end_wd");
            String isUsed = (String) element.get("use_yn");
            String start = (String) element.get("c_dt_strt");
            String end = (String) element.get("c_dt_end");
            Integer hotAirCnt = ifNullReturnZero(element, "heat1_cnt");
            Integer heaterCnt = ifNullReturnZero(element, "heat2_cnt");
            Integer fireplaceCnt = ifNullReturnZero(element, "heat3_cnt");
            Integer radiatorCnt = ifNullReturnZero(element, "heat4_cnt");

            Double minX = (Double) element.get("g2_xmin");
            Double minY = (Double) element.get("g2_ymin");
            double x = removeFactor(minX / 100);
            double y = removeFactor(minY / 100);

            LatLng latLng = coordConverter.xyToLatLng(x, y);
            double latitude = latLng.getLat();
            double longitude = latLng.getLng();

            coldShelterRepository.save(ColdShelter.builder()
                            .name(name)
                            .roadAddress(roadAddress)
                            .startWeekday(startWeekday)
                            .endWeekday(endWeekday)
                            .startHoliday(startHoliday)
                            .endHoliday(endHoliday)
                            .isUsed(isUsed)
                            .start(start)
                            .end(end)
                            .latitude(latitude)
                            .longitude(longitude)
                            .hotAirCnt(hotAirCnt)
                            .heaterCnt(heaterCnt)
                            .fireplaceCnt(fireplaceCnt)
                            .radiatorCnt(radiatorCnt)
                            .build());
        }
    }

    private int ifNullReturnZero(JSONObject element, String key) {
        Object object = element.get(key);

        if (object == null) {
            return 0;
        }

        return ((Long) object).intValue();
    }

    private double removeFactor(double num) {
        BigDecimal bigDecimal = new BigDecimal(num);
        String str = bigDecimal.toString();
        return Double.parseDouble(str);
    }

    private double calculate(LatLng src, LatLng dst) {
        double latDist = Math.abs(src.getLat() - dst.getLat());
        double lngDist = Math.abs(src.getLng() - dst.getLng());

        return Math.sqrt(latDist * latDist + lngDist * lngDist);
    }
}
