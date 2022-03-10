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
	private String id;
	private String name;
	private String pokemonSprite;
	private String height;
	private String weight;
	private List<String> type;
	private List<String> abilities;
	private String hp;
	private String attack;
	private String defense;
	private String specialAttack;
	private String specialDefense;
	private String speed;
	
	public Pokemon(String id, String name, String pokemonSprite, String height, String weight, List<String> type,
			List<String> abilities, String hp, String attack, String defense, String specialAttack,
			String specialDefense, String speed) {
		this.id = id;
		this.name = name;
		this.pokemonSprite = pokemonSprite;
		this.height = height;
		this.weight = weight;
		this.type = type;
		this.abilities = abilities;
		this.hp = hp;
		this.attack = attack;
		this.defense = defense;
		this.specialAttack = specialAttack;
		this.specialDefense = specialDefense;
		this.speed = speed;
	}

}
