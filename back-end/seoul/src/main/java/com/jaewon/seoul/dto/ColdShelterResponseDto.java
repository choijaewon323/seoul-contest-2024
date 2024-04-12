package com.jaewon.seoul.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ColdShelterResponseDto {
    private String name;
    private String roadAddress;
    private String startWeekday;
    private String endWeekday;
    private String startHoliday;
    private String endHoliday;
    private String isUsed;
    private String start;
    private String end;
    private Double latitude;
    private Double longitude;
    private Integer hotAirCnt;
    private Integer heaterCnt;
    private Integer fireplaceCnt;
    private Integer radiatorCnt;

    @Builder
    public ColdShelterResponseDto(String name, String roadAddress, String startWeekday, String endWeekday,
                                  String startHoliday, String endHoliday, String isUsed, String start, String end,
                                  Double latitude, Double longitude,
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
}
