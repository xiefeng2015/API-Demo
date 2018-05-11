package com.example.utils.auth;

import com.example.auth.HeaderBasicAccess;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.collections.Maps;
import org.testng.util.Strings;

import java.sql.Timestamp;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.path.json.JsonPath.from;

public class AuthUtils {

    public static String ACCESS_TOKEN;

    static {
        ACCESS_TOKEN = getAccessToken();
    }

    private static String getAccessToken() {
        String mApi = "http://cai.pre.wesai.com/auth";
        Map<String, Object> payloads = Maps.newHashMap();
        payloads.put("refresh_token", "");
        payloads.put("code", "");
        payloads.put("grant_type", "client_credentials");
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Authorization", HeaderBasicAccess.getAuthorHeaderInfo());
        headers.put("User-Agent", " okhttp/3.4.1");
        headers.put("Accept-Encoding", " gzip");
        Response response =
                given().log().ifValidationFails()
                        .contentType(JSON).headers(headers).body(payloads).when().post(mApi);
        // log response
        response.then().log().ifValidationFails();
        String token = from(response.asString()).getString("access_token");
        return Strings.isNotNullAndNotEmpty(token) ? token : "";
    }

}
