package com.jaewon.seoul.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CoordConverterTests {
    CoordConverter coordConverter = new CoordConverter();

    @DisplayName("xy-중부원점 좌표에서 위경도 변환 테스트")
    @Test
    void test() throws Exception {
        // given
        double x = 207171.2800;
        double y = 555972.6500;

        double x1 = 207024.8600;
        double y1 = 555538.5600;

        // when
        LatLng result = coordConverter.xyToLatLng(x, y);
        LatLng result2 = coordConverter.xyToLatLng(x1, y1);

        // then
        System.out.println("lat : " + result.getLat());
        System.out.println("lng : " + result.getLng());
        System.out.println();
        System.out.println("lat : " + result2.getLat());
        System.out.println("lng : " + result2.getLng());


    }
}
