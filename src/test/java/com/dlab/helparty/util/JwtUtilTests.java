package com.dlab.helparty.util;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JwtUtilTests {

    private static String SECRET = "12345678901234567890123456789012";

    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil(SECRET);
    }

    @Test
    public void createToken(){
        String token = jwtUtil.createToken(1004L, "John");
        assertEquals(token,
                "eyJhbGciOiJIUzI1NiJ9" +
                        ".eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0" +
                        ".8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A");
    }

    @Test
    public void getClaim(){
        String token = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJ1c2VySWQiOjEwMDQsIm5hbWUiOiJKb2huIn0" +
                ".8hm6ZOJykSINHxL-rf0yV882fApL3hyQ9-WGlJUyo2A";
        Claims claims = jwtUtil.getClaim(token);

        assertEquals(claims.get("userId", Long.class), 1004L);
        assertEquals(claims.get("name"), "John");
    }

}