package io.uouo.wechatbot;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("io.uouo.wechatbot.mapper")
@SpringBootApplication
public class WechatBotApplication {
    public static String[] args;
    public static ConfigurableApplicationContext context;

    public static void main(String[] args) {
        WechatBotApplication.args = args;
        WechatBotApplication.context = SpringApplication.run(WechatBotApplication.class, args);
    }

}
