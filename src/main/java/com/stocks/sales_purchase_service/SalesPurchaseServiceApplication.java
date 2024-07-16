package com.stocks.sales_purchase_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.stocks.product_service.model","com.stocks.user_service.model","package com.stocks.sales_purchase_service.model"})
public class SalesPurchaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesPurchaseServiceApplication.class, args);
	}

}
