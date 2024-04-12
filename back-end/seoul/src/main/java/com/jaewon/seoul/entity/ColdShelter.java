package com.jaewon.seoul.entity;

import com.jaewon.seoul.dto.ColdShelterResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cold_shelter")
public class ColdShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "road_address")
    private String roadAddress;
    @Column(name = "start_weekday")
    private String startWeekday;
    @Column(name = "end_weekday")
    private String endWeekday;
    @Column(name = "start_holiday")
    private String startHoliday;
    @Column(name = "end_holiday")
    private String endHoliday;
    @Column(name = "is_used")
    private String isUsed;
    private String start;
    private String end;
    private Double latitude;
    private Double longitude;
    @Column(name = "hot_air_cnt")
    private Integer hotAirCnt;
    @Column(name = "heater_cnt")
    private Integer heaterCnt;
    @Column(name = "fireplace_cnt")
    private Integer fireplaceCnt;
    @Column(name = "radiator_cnt")
    private Integer radiatorCnt;

    @Builder
    public ColdShelter(String name, String roadAddress, String startWeekday,
                       String endWeekday, String startHoliday, String endHoliday,
                       String isUsed, String start, String end, Double latitude, Double longitude,
                       Integer hotAirCnt, Integer heaterCnt, Integer fireplaceCnt, Integer radiatorCnt) {
        this.name = name;
        this.roadAddress = roadAddress;
        this.startWeekday = startWeekday;
        this.endWeekday = endWeekday;
        this.startHoliday = startHoliday;
        this.endHoliday = endHoliday;
        this.isUsed = isUsed;
        this.start = start;
        this.end = end;
        this.latitude = latitude;
        this.longitude = longitude;
        this.hotAirCnt = hotAirCnt;
        this.heaterCnt = heaterCnt;
        this.fireplaceCnt = fireplaceCnt;
        this.radiatorCnt = radiatorCnt;
    }

    public ColdShelterResponseDto toResponseDto() {
        return ColdShelterResponseDto.builder()
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
                .build();
    }
}
