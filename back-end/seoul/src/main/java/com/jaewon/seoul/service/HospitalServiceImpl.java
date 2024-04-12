package com.jaewon.seoul.service;

import com.jaewon.seoul.dto.HospitalResponseDto;
import com.jaewon.seoul.dto.HotShelterResponseDto;
import com.jaewon.seoul.entity.Hospital;
import com.jaewon.seoul.entity.HotShelter;
import com.jaewon.seoul.repository.HospitalRepository;
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

@Transactional
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;
    private final ResourceLoader resourceLoader;

    public HospitalServiceImpl(HospitalRepository hospitalRepository, ResourceLoader resourceLoader) {
        this.hospitalRepository = hospitalRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalResponseDto> getList(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<Hospital> hospitals = hospitalRepository.findByLatAndLngBetween(startLat, endLat, startLng, endLng);

        List<HospitalResponseDto> results = new ArrayList<>();
        hospitals.stream().forEach((e) -> {
            results.add(e.toResponseDto());
        });

        return results;
    }

    @Override
    @Transactional(readOnly = true)
    public List<HospitalResponseDto> getNearThree(LatLng latLng) {
        final double lat = latLng.getLat();
        final double lng = latLng.getLng();

        final double startLat = lat - Unit.LAT.getUnit();
        final double endLat = lat + Unit.LAT.getUnit();
        final double startLng = lng - Unit.LNG.getUnit();
        final double endLng = lng + Unit.LNG.getUnit();

        List<Hospital> hospitals = hospitalRepository.findAll();

        if (hospitals.isEmpty()) {
            return new ArrayList<>();
        }

        hospitals.sort((e1, e2) -> {
            double e1Dist = calculate(latLng, new LatLng(e1.getLatitude(), e1.getLongitude()));
            double e2Dist = calculate(latLng, new LatLng(e2.getLatitude(), e2.getLongitude()));

            if (e1Dist < e2Dist) {
                return -1;
            }
            return 1;
        });

        List<HospitalResponseDto> results = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            results.add(hospitals.get(i).toResponseDto());
        }

        return results;
    }

    @Override
    public void update() throws Exception {
        final String path = "classpath:static/hospital.json";
        final File file = resourceLoader.getResource(path).getFile();
        final Reader reader = new FileReader(file);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
        JSONArray data = (JSONArray) jsonObject.get("DATA");

        for (int i = 0; i < data.size(); i++) {
            JSONObject element = (JSONObject) data.get(i);

            String name = (String) element.get("dutyname");
            String info = (String) element.get("dutyinf");
            String roadAddress = (String) element.get("dutyaddr");
            String category = (String) element.get("dutydivnam");

            String tel = (String) element.get("dutytel1");
            String emergencyTel = (String) element.get("dutytel3");

            Double latitude = Double.valueOf((String) element.get("wgs84lat"));
            Double longitude = Double.valueOf((String) element.get("wgs84lon"));

            String mondayStart = (String) element.get("dutytime1s");
            String tuesdayStart = (String) element.get("dutytime2s");
            String wednesdayStart = (String) element.get("dutytime3s");
            String thursdayStart = (String) element.get("dutytime4s");
            String fridayStart = (String) element.get("dutytime5s");
            String saturdayStart = (String) element.get("dutytime6s");
            String sundayStart = (String) element.get("dutytime7s");
            String holidayStart = (String) element.get("dutytime8s");

            String mondayEnd = (String) element.get("dutytime1c");
            String tuesdayEnd = (String) element.get("dutytime2c");
            String wednesdayEnd = (String) element.get("dutytime3c");
            String thursdayEnd = (String) element.get("dutytime4c");
            String fridayEnd = (String) element.get("dutytime5c");
            String saturdayEnd = (String) element.get("dutytime6c");
            String sundayEnd = (String) element.get("dutytime7c");
            String holidayEnd = (String) element.get("dutytime8c");

            String monday = timeConcat(mondayStart, mondayEnd);
            String tuesday = timeConcat(tuesdayStart, tuesdayEnd);
            String wednesday = timeConcat(wednesdayStart, wednesdayEnd);
            String thursday = timeConcat(thursdayStart, thursdayEnd);
            String friday = timeConcat(fridayStart, fridayEnd);
            String saturday = timeConcat(saturdayStart, saturdayEnd);
            String sunday = timeConcat(sundayStart, sundayEnd);
            String holiday = timeConcat(holidayStart, holidayEnd);

            hospitalRepository.save(Hospital.builder()
                            .name(name)
                            .info(info)
                            .roadAddress(roadAddress)
                            .category(category)
                            .tel(tel)
                            .emergencyTel(emergencyTel)
                            .latitude(latitude)
                            .longitude(longitude)
                            .monday(monday)
                            .tuesday(tuesday)
                            .wednesday(wednesday)
                            .thursday(thursday)
                            .friday(friday)
                            .saturday(saturday)
                            .sunday(sunday)
                            .holiday(holiday)
                            .build());
        }
    }

    private String timeConcat(String start, String end) {
        StringBuilder sb = new StringBuilder();
        sb.append(start);
        sb.append(" ~ ");
        sb.append(end);

        return sb.toString();
    }

    private double calculate(LatLng src, LatLng dst) {
        double latDist = Math.abs(src.getLat() - dst.getLat());
        double lngDist = Math.abs(src.getLng() - dst.getLng());

        return Math.sqrt(latDist * latDist + lngDist * lngDist);
    }
}
