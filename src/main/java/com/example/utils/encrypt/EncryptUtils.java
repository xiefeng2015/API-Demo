package com.example.utils.encrypt;

import com.example.utils.DateUtil;
import com.example.utils.MD5;
import com.example.utils.StringUtils;
import com.example.utils.http.UrlUtil;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class EncryptUtils {

    public UrlEncodedFormEntity getFormEntity(Map<String, Object> params, Charset... ucode) {
        Charset f;
        Charset s = Consts.UTF_8;
        f = ucode.equals("") ? Consts.UTF_8 : s;
        List<NameValuePair> formParams = Lists.newArrayList();
        try {
            params.forEach((k, v) -> formParams.add(new BasicNameValuePair(k, v.toString())));
            return new UrlEncodedFormEntity(formParams, f);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Map<String, Object> getSign(Map<String, Object> params) {
        String ts, time, sign;
        //t: 为当前接口请求的10位时间戳，时区统一为中国标准时间：China Standard
        ts = DateUtil.getTimeStamp();
        time = ts;
        //s: 为入参抽样MD5码：
        sign = sign(params, "ba27af44-0de1-f374-8983-2253e331bb1c", time);
        Map<String, Object> data = Maps.newHashMap();
        data.put("t", time);
        data.put("s", sign);
        return data;
    }

    public String sign(Map<String, ? extends Object> data, String signKey, String timestamp) {
        String string = "", signature = "";
        Map<String, String> resultMap = (StringUtils.sortMapByKey((Map<String, String>) data));
        for (Map.Entry<String, String> entry : resultMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            if (entry.getValue() instanceof String) {
                string += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        string = UrlUtil.getURLEncoderString(string.substring(0, string.length() - 1));
        string = string.replace("%3D", "=").replace("%26", "&");
        string = string.indexOf("*") != -1 ? string.replace("*", "%2A") : string;
        string = string.indexOf("[") != -1 ? string.replace("[", "%5B") : string;
        string = string.indexOf("]") != -1 ? string.replace("]", "%5D") : string;
        if (String.valueOf(data.get("betCodes")).contains("&")) {
            string = string.replaceFirst("&", "%26");
        }
        System.out.println(MD5.md5(string));
        signature = MD5.md5(string + timestamp);
        return signature.toUpperCase();
    }

}
