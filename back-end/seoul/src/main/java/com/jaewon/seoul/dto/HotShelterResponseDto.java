package com.jaewon.seoul.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HotShelterResponseDto {
    private String name;
    private String roadAddress;
    private Double longitude;
    private Double latitude;
    private String start;
    private String end;
    private Integer airConditionerCnt;
    private Integer fanCnt;
    private String isUsed;
    private String isHolidayOpen;

    @Builder
    public HotShelterResponseDto(String name, String roadAddress, Double longitude, Double latitude,
                                 String start, String end, Integer airConditionerCnt, Integer fanCnt,
                                 String isUsed, String isHolidayOpen) {
        this.name = name;
        this.roadAddress = roadAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.start = start;
        this.end = end;
        this.airConditionerCnt = airConditionerCnt;
        this.fanCnt = fanCnt;
        this.isUsed = isUsed;
        this.isHolidayOpen = isHolidayOpen;
    }
}
