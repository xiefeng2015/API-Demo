package com.example.auth.tests;

import com.example.annotation.Path;
import com.example.testng.BaseTest;
import com.example.utils.auth.AuthUtils;
import com.example.utils.encrypt.EncryptUtils;
import org.testng.annotations.Test;
import org.testng.collections.Maps;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static com.example.http.StatusCode.OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class WordCup extends BaseTest {
    @Test
    @Path("/v2/sporttery/world/cup/champion/odds")
    public void CGJ_odds_Test() {
        EncryptUtils encryptUtils = new EncryptUtils();
        Map<String, Object> payload = Maps.newHashMap();
        payload.put("1", "1");
        given().log().ifValidationFails()
                .header("Token", AuthUtils.ACCESS_TOKEN)
                .queryParams(payload)
                .queryParam("t", encryptUtils.getSign(payload).get("t").toString())
                .queryParam("s", encryptUtils.getSign(payload).get("s").toString())
                .get(mApi)
                .then().log().body()
                .statusCode(OK.value());
    }

    @Test
    @Path("/v2/sporttery/world/cup/gyj/country")
    public void GYJ_CountryTest(){
        EncryptUtils encryptUtils = new EncryptUtils();
        Map<String, Object> payload = Maps.newHashMap();
        payload.put("1", "1");
        List<String> strings = Arrays.asList(new String[]{"巴西", "德国", "西班牙", "阿根廷", "法国",
                "比利时", "葡萄牙", "英格兰", "乌拉圭", "哥伦比亚", "克罗地亚", "俄罗斯"});
        given().log().ifValidationFails()
                .header("Token", AuthUtils.ACCESS_TOKEN)
                .queryParams(payload)
                .queryParam("t", encryptUtils.getSign(payload).get("t").toString())
                .queryParam("s", encryptUtils.getSign(payload).get("s").toString())
                .get(mApi)
                .then().log().ifValidationFails()
                .statusCode(OK.value())
                .body("data.countrys", equalTo(strings));
    }

    @Test
    @Path("/v2/sporttery/world/cup/gyj/odds")
    public void GYJ_OddsTest(){
        EncryptUtils encryptUtils = new EncryptUtils();
        Map<String, Object> payload = Maps.newHashMap();
        payload.put("1", "1");
        given().log().ifValidationFails()
                .header("Token", AuthUtils.ACCESS_TOKEN)
                .queryParams(payload)
                .queryParam("t", encryptUtils.getSign(payload).get("t").toString())
                .queryParam("s", encryptUtils.getSign(payload).get("s").toString())
                .get(mApi)
                .then().log().ifValidationFails()
                .statusCode(OK.value());
    }

}

