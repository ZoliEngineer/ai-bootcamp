package com.epam.training.gen.ai.semantic.plugins;

import org.apache.logging.log4j.util.Strings;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import lombok.extern.slf4j.Slf4j;

/**
 * A simple plugin that defines a kernel function for performing a basic action
 * on data.
 * <p>
 * This plugin exposes a method to be invoked by the kernel, which logs and
 * returns the input query.
 */
@Slf4j
public class WeatherPlugin {

	@Tool(description = "Gets weather forecast for a location")
	public String getWeatherForecast(
			@ToolParam(description =  "City name which the weather forecast is for") String city) {
		log.info("Weather plugin was called with city: [{}]", city);
		
		return getMockWeatherForecast(city);
		
	}
	
	private String getMockWeatherForecast(String city) {
		if(Strings.isEmpty(city)) {
			log.error("City not provided");
			return "Error, city name is not provided";
		} else {
			return "Very cold, -30 Celsius, with chance of snowstorm";
		}
	}
}
