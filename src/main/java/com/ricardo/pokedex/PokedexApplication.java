package com.ricardo.pokedex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PokedexApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PokedexApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Initializing the Pokédex API");
		SpringApplication.run(PokedexApplication.class, args);
		LOGGER.info("API started successfully");
	}

}
