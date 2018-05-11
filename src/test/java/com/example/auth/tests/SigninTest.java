package com.example.auth.tests;

import com.example.annotation.Path;
import com.example.auth.HeaderBasicAccess;
import com.example.testng.BaseTest;
import com.example.utils.auth.AuthUtils;
import com.example.utils.encrypt.EncryptUtils;
import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import org.testng.collections.Maps;

import java.util.Map;

import static com.example.http.StatusCode.OK;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class SigninTest extends BaseTest {

    @Test
    @Path("/auth")
    public void USER_AUTHEN_TEST() {
        Map<String, Object> payloads = Maps.newHashMap();
        payloads.put("refresh_token", "");
        payloads.put("code", "");
        payloads.put("grant_type", "client_credentials");
        Map<String, String> headers = Maps.newHashMap();
        headers.put("Authorization", HeaderBasicAccess.getAuthorHeaderInfo());
        headers.put("User-Agent", " okhttp/3.4.1");
        headers.put("Accept-Encoding", " gzip");

        given().log().ifValidationFails().contentType(JSON)
                .headers(headers)
                .body(payloads)
                .when()
                .post(mApi)
                .then().log().ifValidationFails()
                .statusCode(OK.value())
                .body("expried_in", is(604800));
    }

    @Test
    @Path("/v2/lottery/user/login")
    public void LOTTERY_LOGIN_TEST() {
        EncryptUtils encrypt = new EncryptUtils();
        String mobile = config.mobilenumber();
        String password = config.password();
        Map<String, Object> headers = Maps.newHashMap();
        headers.put("token", AuthUtils.ACCESS_TOKEN);
        Map<String, Object> payload = Maps.newHashMap();
        payload.put("mobile", mobile);
        payload.put("password", password);

        given().log().ifValidationFails()
                .config(RestAssured.config()
                        .encoderConfig(EncoderConfig.encoderConfig()
                                .encodeContentTypeAs("x-www-form-urlencoded",
                                        ContentType.URLENC)))
                .contentType(ContentType.URLENC.withCharset("UTF-8"))
                .queryParam("t", encrypt.getSign(payload).get("t").toString())
                .queryParam("s", encrypt.getSign(payload).get("s").toString())
                .header("Token", AuthUtils.ACCESS_TOKEN)
                .formParams(payload)
                .post(mApi)
                .then().log().ifValidationFails()
                .statusCode(OK.value())
                .body("data.nickname", equalTo("鹿旅途"))
                .body("data.mobile", equalTo(mobile))
                .body("data.passworded", is(1));
    }


}
