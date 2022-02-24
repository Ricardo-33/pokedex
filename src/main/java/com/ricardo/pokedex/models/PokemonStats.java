package com.ricardo.pokedex.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonStats {
	
	private String name;
	private String base_stat;
	
	public PokemonStats (String name, String base_stat) {
		this.name = name;
		this.base_stat = base_stat;
	}
}
