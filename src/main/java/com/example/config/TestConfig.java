package com.example.config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:testconfig.properties")
public interface TestConfig extends Config {

    @Key("test.host")
    @DefaultValue("http://cai.pre.wesai.com")
    String host();

    @Key("protocol")
    @DefaultValue("http")
    String protocal();

    @Key("mobile.number")
    @DefaultValue("15882066239")
    String mobilenumber();

    @Key("password")
    @DefaultValue("lucky20180101")
    String password();
}
