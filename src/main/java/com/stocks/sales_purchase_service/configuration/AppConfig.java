package com.stocks.sales_purchase_service.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    /**
     * Configures a RestTemplate bean to be used for making HTTP requests.
     *
     * @return RestTemplate instance configured with default settings.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
