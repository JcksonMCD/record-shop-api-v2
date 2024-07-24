package com.northcoders.record_shop_api_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@SpringBootApplication
@EnableCaching
public class RecordShopApiV2Application {

	public static void main(String[] args) {
		SpringApplication.run(RecordShopApiV2Application.class, args);
	}

}
