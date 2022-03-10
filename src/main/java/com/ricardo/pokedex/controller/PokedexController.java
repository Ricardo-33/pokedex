package com.ricardo.pokedex.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pokedex.client.PokedexClient;
import com.ricardo.pokedex.models.Pokemon;
import com.ricardo.pokedex.repository.PokedexRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/pokemons")
public class PokedexController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PokedexController.class);
		
	@Autowired
	PokedexRepository pokedexRepository;
	
	PokedexClient pokedexClient;
	
	@GetMapping("/{name}")
	public Pokemon getPokemonByName(@PathVariable String name) {
		LOGGER.info("Get method initialized");
		return pokedexClient.findPokemonByName(name);
	}

}
