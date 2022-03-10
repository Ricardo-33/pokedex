package com.ricardo.pokedex.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ricardo.pokedex.models.Pokemon;

@Repository("pokemon")
public interface PokedexRepository extends MongoRepository<Pokemon, String> {
	
	public Optional<Pokemon> findByName(String name);

}
