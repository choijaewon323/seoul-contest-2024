package com.jaewon.seoul.entity;

import com.jaewon.seoul.dto.HotShelterResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hot_shelter")
public class HotShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name = "area_code")
    private String areaCode;
    @Column(name = "road_address")
    private String roadAddress;
    @Column(name = "old_address")
    private String oldAddress;
    private Double longitude;
    private Double latitude;
    private Double y;
    private Double x;
    private String start;
    private String end;
    @Column(name = "air_conditioner_cnt")
    private Integer airConditionerCnt;
    @Column(name = "fan_cnt")
    private Integer fanCnt;
    @Column(name = "is_used")
    private String isUsed;
    @Column(name = "is_holiday_open")
    private String isHolidayOpen;

    @Builder
    public HotShelter(String name, String areaCode, String roadAddress, String oldAddress,
                      Double longitude, Double latitude, Double y, Double x,
                      String start, String end,
                      Integer airConditionerCnt, Integer fanCnt,
                      String isUsed, String isHolidayOpen) {
        this.name = name;
        this.areaCode = areaCode;
        this.roadAddress = roadAddress;
        this.oldAddress = oldAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.y = y;
        this.x = x;
        this.start = start;
        this.end = end;
        this.airConditionerCnt = airConditionerCnt;
        this.fanCnt = fanCnt;
        this.isUsed = isUsed;
        this.isHolidayOpen = isHolidayOpen;
    }

    public HotShelterResponseDto toResponseDto() {
        return HotShelterResponseDto.builder()
                .name(name)
                .end(end)
                .airConditionerCnt(airConditionerCnt)
                .fanCnt(fanCnt)
                .isUsed(isUsed)
                .isHolidayOpen(isHolidayOpen)
                .latitude(latitude)
                .longitude(longitude)
                .roadAddress(roadAddress)
                .start(start)
                .build();
    }
}
