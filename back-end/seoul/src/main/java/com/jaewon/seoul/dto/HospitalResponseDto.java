package com.jaewon.seoul.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HospitalResponseDto {
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String holiday;
    private String tel;
    private String emergencyTel;
    private String roadAddress;
    private Double longitude;
    private Double latitude;
    private String info;
    private String name;

    @Builder
    public HospitalResponseDto(String monday, String tuesday, String wednesday, String thursday, String friday,
                               String saturday, String sunday, String holiday, String tel, String emergencyTel,
                               String roadAddress, Double longitude, Double latitude, String info, String name) {
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.holiday = holiday;
        this.tel = tel;
        this.emergencyTel = emergencyTel;
        this.roadAddress = roadAddress;
        this.longitude = longitude;
        this.latitude = latitude;
        this.info = info;
        this.name = name;
    }
}
