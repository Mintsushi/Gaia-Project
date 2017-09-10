package com.example.round.neoful16;

/**
 * Created by Round on 2017-07-16.
 */
public class Constants {

    //동네예보정보조회서비스 API_KEY
    public static final String WEATHER_OPEN_API_KEY = "hnwENP2C7ReDLarwDMWH79Ji0atrixuwHP4lqvkIDLhU1veM1tIlKoNV23pR5VmOA5PPAVy%2F0Nk3vtxkuV1n4Q%3D%3D";

    //동네예보정보조회서비스 URL
    public static final String WEATHER_SERVER_PREFIX = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/";

    //실화 조회
    public static final String WEATHER_SERVICE_GRIB = "ForecastGrib";

    //초단기 예보조회
    public static final String WEATHER_SERVICE_TIMEDATA = "ForecastTimeData";

    //단기 예보조회
    public static final String WEATHER_SERVICE_SPACEDATA = "ForecastSpaceData";

    //Parameter1 : api key
    public static final String WEATHER_PARAM_SERVICE_KEY = "ServiceKey=";
    //Parameter2 : type
    public static final String WEATHER_PARAM_TYPE = "_type=json";
    public static final String WEATHER_PARAM_TYPE_VALUE = "json";

}
