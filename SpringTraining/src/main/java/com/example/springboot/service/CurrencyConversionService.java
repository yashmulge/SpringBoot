package com.example.springboot.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyConversionService {

    @Value("${currency.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public CurrencyConversionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public double convertUsdToInr(double amountInUsd) {
        // Fetch the exchange rate
        String url = apiUrl;
        var response = restTemplate.getForObject(url, ExchangeRateResponse.class);
        if (response != null) {
            double rate = response.getRates().get("INR");
            return amountInUsd * rate;
        }
        throw new RuntimeException("Unable to fetch exchange rate");
    }

    // Define a class for the response structure
    public static class ExchangeRateResponse {
        private String base;
        private Map<String, Double> rates;

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}