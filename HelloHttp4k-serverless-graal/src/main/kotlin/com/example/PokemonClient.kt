package com.example

import com.example.PokemonMoshi.auto
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import se.ansman.kotshi.JsonSerializable

class PokemonClient(private val pokemonClient: HttpHandler) {

    private val body = Body.auto<Results>().toLens()

    fun list(): List<Pokemon> {
        val response = pokemonClient(Request(Method.GET, "/api/v2/pokemon?limit=100"))
        return body(response).results
    }
}

@JsonSerializable
data class Pokemon(val name: String)

@JsonSerializable
data class Results(val results: List<Pokemon>)