package com.ricardo.pokedex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.pokedex.client.PokedexClient;
import com.ricardo.pokedex.repository.PokedexRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/pokemons")
public class PokedexController {
		
	@Autowired
	PokedexRepository pokedexRepository;
	
	PokedexClient pokedexClient;
	
	@GetMapping("/{name}")
	public void getPokemonByName(@PathVariable String name) {
		log.info("Get method initialized");
		pokedexClient.findPokemonByName(name);
	}

}
