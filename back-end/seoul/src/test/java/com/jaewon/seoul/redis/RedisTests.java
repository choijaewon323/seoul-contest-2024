package com.jaewon.seoul.redis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RedisTests {
    @Autowired
    RedisTemplate<String, Integer> redisTemplate;

    @DisplayName("동작 테스트")
    @Test
    void test1() {
        // given
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("20240323", 26);

        // when
        int result = valueOperations.get("20240323");

        // then
        assertThat(result).isEqualTo(26);
    }

    @DisplayName("redisTemplate hasKey 테스트")
    @Test
    void test2() {
        // given
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("20240323", 26);

        // when
        boolean result = redisTemplate.hasKey("20240323");

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("get 함수 실행 시 없을 때 테스트")
    @Test
    void test3() {
        // given
        ValueOperations<String, Integer> valueOperations = redisTemplate.opsForValue();

        // when
        Integer result = valueOperations.get("20240324");

        // then
        assertThat(result).isNull();
    }

    @DisplayName("hasKey 없을 때 테스트")
    @Test
    void test4() {
        // when
        boolean result = redisTemplate.hasKey("20240324");

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("expire 테스트")
    @Test
    void test5() throws Exception {
        // given
        String key = "1234";
        int value = 1234;
        ValueOperations<String, Integer> vo = redisTemplate.opsForValue();

        // when
        vo.set(key, value);
        redisTemplate.expire("1234", 3, TimeUnit.SECONDS);
        Thread.sleep(5000);

        // then
        Integer result = vo.get(key);
        assertThat(result).isNull();
    }
}
