package com.coding.huang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Created by Administrator on 2019/10/22.
 */
@SpringBootApplication
@ServletComponentScan
public class MainApplication {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(MainApplication.class,args);
    }
}