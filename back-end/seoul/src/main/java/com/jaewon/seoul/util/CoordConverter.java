package com.jaewon.seoul.util;

import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

@Component
public class CoordConverter {

    private final CRSFactory crsFactory;
    private final CoordinateReferenceSystem WGS84;
    private final CoordinateReferenceSystem GRS80_MIDDLE;
    private final CoordinateTransformFactory ctFactory;
    private final CoordinateTransform grsToWgs;

    public CoordConverter() {
        crsFactory = new CRSFactory();
        WGS84 = crsFactory.createFromName("epsg:4326");
        GRS80_MIDDLE = crsFactory.createFromName("epsg:5186");
        ctFactory = new CoordinateTransformFactory();
        grsToWgs = ctFactory.createTransform(GRS80_MIDDLE, WGS84);
    }

    public LatLng xyToLatLng(double x, double y) {
        ProjCoordinate result = new ProjCoordinate();
        grsToWgs.transform(new ProjCoordinate(x, y), result);

        double lat = result.y;
        double lng = result.x;

        return new LatLng(lat, lng);
    }
}
