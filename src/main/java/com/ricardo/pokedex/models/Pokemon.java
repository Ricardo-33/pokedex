package com.ricardo.pokedex.models;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "pokemon")
public class Pokemon implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;
	private String id;
	private String pokemonSprite;
	private String height;
	private String weight;
	private List<String> type;
	private List<String> abilities;
//	private List<HashMap<String,String>> pokemonStats;
//	private List<PokemonStats> pokemonStats;
	private List<String> pokemonStatName;
	private List<String> pokemonBaseStat;
	
	public Pokemon(String name, String id, String pokemonSprite, String height, String weight, List<String> type,
			List<String> abilities, List<String> pokemonStatName, List<String> pokemonBaseStat) {
		super();
		this.name = name;
		this.id = id;
		this.pokemonSprite = pokemonSprite;
		this.height = height;
		this.weight = weight;
		this.type = type;
		this.abilities = abilities;
		this.pokemonStatName = pokemonStatName;
		this.pokemonBaseStat = pokemonBaseStat;
	}

}
