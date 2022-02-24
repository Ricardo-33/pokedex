package com.ricardo.pokedex.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ricardo.pokedex.models.Pokemon;
import com.ricardo.pokedex.repository.PokedexRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PokedexClient {

	@Autowired
	PokedexRepository pokedexRepository;

	public void findPokemonByName(String pokemonName) {
		try {

			URL url = new URL("https://pokeapi.co/api/v2/pokemon/" + pokemonName);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
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

			JsonArray jsonArrayObjectStats = jsonObject.get("stats").getAsJsonArray();

//	    		List<PokemonStats> pokemonStats = new ArrayList<>();
//	    		for (int i = 0; i < jsonArrayObjectStats.size(); i++) {
//					String statName = jsonArrayObjectStats.get(i).getAsJsonObject().get("stat").getAsJsonObject()
//							.get("name").getAsString();
//					String baseStat = jsonArrayObjectStats.get(i).getAsJsonObject().get("base_stat").getAsString();
//					
//					PokemonStats pk = new PokemonStats(statName, baseStat);
//					pokemonStats.add(pk);
//	    		}
//	    		System.out.println(pokemonStats);

//	    		List<HashMap<String, String>> pokemonStats = new ArrayList<>();
//	    		HashMap<String, String> pokemonStat = new HashMap<>();
//	    		for (int i = 0; i < jsonArrayObjectStats.size(); i++) {
//	    			String statName = jsonArrayObjectStats.get(i).getAsJsonObject().get("stat").getAsJsonObject().get("name")
//	    					.getAsString();
//	    			String baseStat = jsonArrayObjectStats.get(i).getAsJsonObject().get("base_stat").getAsString();
//	    			
//	    			pokemonStat.put("name", statName);
//	    			pokemonStat.put("base_stat", baseStat);
//	    			
//	    			pokemonStats.add(pokemonStat);	
//	    		}

			List<String> pokemonStatName = new ArrayList<>();
			for (int i = 0; i < jsonArrayObjectStats.size(); i++) {
				String statName = jsonArrayObjectStats.get(i).getAsJsonObject().get("stat").getAsJsonObject()
						.get("name").getAsString();
				pokemonStatName.add(statName);
			}

			List<String> pokemonBaseStat = new ArrayList<>();
			for (int i = 0; i < jsonArrayObjectStats.size(); i++) {
				String baseStat = jsonArrayObjectStats.get(i).getAsJsonObject().get("base_stat").getAsString();
				pokemonBaseStat.add(baseStat);
			}

			Pokemon pokemon = new Pokemon(name, id, pokemonSprite, height, weight, types, abilities, pokemonStatName,
					pokemonBaseStat);

//	    		System.out.println(pokemon);
			pokedexRepository.save(pokemon);

			conn.disconnect();
		} catch (Exception e) {
			System.out.println("Exception in NetClientGet:- " + e);
		}

	}

}
