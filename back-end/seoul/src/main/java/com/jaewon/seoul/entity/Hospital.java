package com.jaewon.seoul.entity;

import com.jaewon.seoul.dto.HospitalResponseDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hospital")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String holiday;

    private String tel;
    @Column(name = "emergency_tel")
    private String emergencyTel;

    @Column(name = "road_address")
    private String roadAddress;

    private Double longitude;
    private Double latitude;

    private String info;
    private String name;
    private String category;

    @Builder
    public Hospital(String monday, String tuesday, String wednesday, String thursday, String friday,
                    String saturday, String sunday, String holiday, String tel, String emergencyTel,
                    String roadAddress, Double longitude, Double latitude, String info, String name, String category) {
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
        this.category = category;
    }

    public HospitalResponseDto toResponseDto() {
        return HospitalResponseDto.builder()
                .monday(monday)
                .tuesday(tuesday)
                .wednesday(wednesday)
                .thursday(thursday)
                .friday(friday)
                .saturday(saturday)
                .sunday(sunday)
                .holiday(holiday)
                .tel(tel)
                .emergencyTel(emergencyTel)
                .roadAddress(roadAddress)
                .longitude(longitude)
                .latitude(latitude)
                .info(info)
                .name(name)
                .build();
    }
}
