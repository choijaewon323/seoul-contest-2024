package com.jaewon.seoul.service;

import com.jaewon.seoul.security.Secret;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional
public class WeatherServiceImpl implements WeatherService {

    private final RedisTemplate<String, Integer> redisTemplate;

    public WeatherServiceImpl(RedisTemplate<String, Integer> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public int getWeather(int nx, int ny) throws Exception {
        String fullKey = fullKey(nx, ny);

        if (redisTemplate.hasKey(fullKey)) {
            log.info("CACHE HIT! : {}", fullKey);
            ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
            return valueOperations.get(fullKey);
        }

        log.info("CACHE MISS!");
        String url = makeUrl(nx, ny);
        BufferedReader reader = sendRequest(url);

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        for (int i = 0; i < item.size(); i++) {
            JSONObject element = (JSONObject) item.get(i);

            String fcstDate = (String) element.get("fcstDate");
            String fcstTime = (String) element.get("fcstTime");
            String fcstValue = (String) element.get("fcstValue");

            String category = (String) element.get("category");

            // 기온
            if (category.equals("T1H")) {

                System.out.println(fcstDate + fcstTime);
                System.out.println(fcstValue + "도");

                String key = dateTimeNxNy(fcstDate, fcstTime, nx, ny);

                valueOperations.set(key, Integer.valueOf(fcstValue));
                redisTemplate.expire(key, 12, TimeUnit.HOURS);
            }
        }

        String key = presentToKey() + nx + ny;

        return valueOperations.get(key);
    }

    private String makeUrl(int nx, int ny) throws UnsupportedEncodingException {
        String[] dateAndTime = getDateAndTime(getHourAgo());
        String baseDate = dateAndTime[0];
        String baseTime = dateAndTime[1];

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + Secret.SECRET_KEY.getValue());
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1000", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(String.valueOf(nx), "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(String.valueOf(ny), "UTF-8"));

        return urlBuilder.toString();
    }

    private BufferedReader sendRequest(String u) throws IOException {
        URL url = new URL(u);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Weather API Response Code : {}", conn.getResponseCode());

        if (isSuccess(conn)) {
            return new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            throw new IllegalStateException("날씨 데이터를 읽을 수 없습니다.");
        }
    }

    private boolean isSuccess(HttpURLConnection conn) throws IOException {
        return (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300);
    }

    private String dateTimeNxNy(String date, String time, int nx, int ny) {
        StringBuilder sb = new StringBuilder();
        sb.append(date);
        sb.append(time);
        sb.append(nx);
        sb.append(ny);
        return sb.toString();
    }

    private String fullKey(int nx, int ny) {
        String dateTime = presentToKey();

        StringBuilder sb = new StringBuilder();
        sb.append(dateTime);
        sb.append(nx);
        sb.append(ny);

        return sb.toString();
    }

    private String presentToKey() {
        LocalDateTime current = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");

        StringBuilder result = new StringBuilder();
        result.append(current.format(formatter));
        result.append("00");

        System.out.println(result.toString());

        return result.toString();
    }

    private String[] getDateAndTime(String formatted) {
        StringTokenizer st = new StringTokenizer(formatted, " ");

        String[] result = new String[2];
        result[0] = st.nextToken();
        result[1] = st.nextToken();
        result[1] += "00";

        System.out.println(result[0] + " and " + result[1]);

        return result;
    }

    private String getHourAgo() {
        LocalDateTime current = LocalDateTime.now();
        LocalDateTime oneHourAgo = current.minusHours(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HH");

        System.out.println(oneHourAgo.format(formatter));

        return oneHourAgo.format(formatter);
    }
}
