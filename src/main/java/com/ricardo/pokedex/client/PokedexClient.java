package com.ricardo.pokedex.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ricardo.pokedex.models.Pokemon;
import com.ricardo.pokedex.repository.PokedexRepository;

@Service
public class PokedexClient {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PokedexClient.class);

	@Autowired
	PokedexRepository pokedexRepository;
		
	public Pokemon findPokemonByName(String pokemonName) {
		Pokemon pokemon = null;
		
		try {
			Optional<Pokemon> pokemonData = pokedexRepository.findByName(pokemonName);		
			if (!pokemonData.isEmpty()) {
				return pokemonData.get();
			}
			
			URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				LOGGER.error("Pokémon: " + pokemonName + " not found in the Poké API. Verify Pokémon's name");
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			
			LOGGER.info("Pokémon's request successfully done.");

			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);

			String output = null;
			String message = new String();
			while ((output = br.readLine()) != null) {
				message += output;
			}

			String jsonString = message;
			JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

			String id = jsonObject.get("id").getAsString();
			String name = jsonObject.get("name").getAsString();
			String height = jsonObject.get("height").getAsString();
			String weight = jsonObject.get("weight").getAsString();

			JsonObject childObjectSprites = jsonObject.getAsJsonObject("sprites");
			String pokemonSprite = childObjectSprites.get("front_default").getAsString();

			JsonArray jsonArrayObjectStats = jsonObject.get("stats").getAsJsonArray();
			String hp = jsonArrayObjectStats.get(0).getAsJsonObject().get("base_stat").getAsString();
			String attack = jsonArrayObjectStats.get(1).getAsJsonObject().get("base_stat").getAsString();
			String defense = jsonArrayObjectStats.get(2).getAsJsonObject().get("base_stat").getAsString();
			String specialAttack = jsonArrayObjectStats.get(3).getAsJsonObject().get("base_stat").getAsString();
			String specialDefense = jsonArrayObjectStats.get(4).getAsJsonObject().get("base_stat").getAsString();
			String speed = jsonArrayObjectStats.get(5).getAsJsonObject().get("base_stat").getAsString();

			JsonArray jsonArrayObjectTypes = jsonObject.get("types").getAsJsonArray();

			List<String> types = new ArrayList<>();
			for (int i = 0; i < jsonArrayObjectTypes.size(); i++) {
				String type = jsonArrayObjectTypes.get(i).getAsJsonObject().get("type").getAsJsonObject().get("name")
						.getAsString();
				types.add(type);
			}

			JsonArray jsonArrayObjectAbilities = jsonObject.get("abilities").getAsJsonArray();

			List<String> abilities = new ArrayList<>();
			for (int i = 0; i < jsonArrayObjectAbilities.size(); i++) {
				String ability = jsonArrayObjectAbilities.get(i).getAsJsonObject().get("ability").getAsJsonObject()
						.get("name").getAsString();
				abilities.add(ability);
			}

			pokemon = new Pokemon(id, name, pokemonSprite, height, weight, types, abilities, hp, attack,
					defense, specialAttack, specialDefense, speed);

			pokedexRepository.save(pokemon);
			LOGGER.info("Pokémon successfully sent to MongoDB");
			conn.disconnect();
			

		} catch (Exception e) {
			LOGGER.error("Pokémon's parameters not matching JsonObject. Message: " + e);
		}
		
		return pokemon;

	}

}
