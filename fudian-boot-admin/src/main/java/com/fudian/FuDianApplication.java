package com.fudian;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.WebApplicationInitializer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 启动程序
 * 
 * @author fudian
 */
@ServletComponentScan
@Slf4j
@EnableSwagger2
@EnableAsync // 开启异步任务
@MapperScan(basePackages = {"com.fudian.**.mapper"})
@SpringBootApplication(scanBasePackages ={"com.fudian"},
        exclude = {
                MongoAutoConfiguration.class,
                DataSourceAutoConfiguration.class,})
public class FuDianApplication extends SpringBootServletInitializer implements WebApplicationInitializer
{
    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application =   SpringApplication.run(FuDianApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = env.getProperty("server.servlet.context-path");
        if (path == null) {
            path="";
        }
        log.info("\n-------------------------------------------------------------\n\t" +
                "Application Hou-Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "swagger-ui: http://" + ip + ":" + port + path + "swagger-ui/index.html\n\t" +
                "knife4j: \thttp://" + ip + ":" + port + path + "doc.html\n" +
                "--------------------------项目启动成功--------------------------");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {

        return builder.sources(FuDianApplication.class);
    }
}
