package com.example.testng;

import com.example.annotation.Path;
import com.example.config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.util.Strings;

import java.lang.reflect.Method;

public class BaseTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected static String host;
    protected static TestConfig config;
    protected String mApi;

    @BeforeClass
    public void beforeClass() {
        config = ConfigFactory.create(TestConfig.class);
        host = config.host();
        logger.info("Testing host: {}", host);
    }

    @BeforeMethod
    public void beforeMethod(Method method) {
        Path path = method.getAnnotation(Path.class);
        if (path != null && Strings.isNotNullAndNotEmpty(path.value())) {
            mApi = host.concat(path.value());
            logger.info("Testing API: {}", mApi);
        }
    }
}
