package com.ricardo.pokedex.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ricardo.pokedex.models.Pokemon;

@Repository
public interface PokedexRepository extends MongoRepository<Pokemon, String> {

}
