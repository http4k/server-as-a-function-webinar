package com.example

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Moshi.auto

/**
 * We have modified the PokemonClient to return an empty list on failure.
 * (defensive but not good practice!)
 */
class PokemonClient(private val pokemonClient: HttpHandler) {
    private val body = Body.auto<Results>().toLens()

    fun list(): List<Pokemon> {
        val response = pokemonClient(Request(Method.GET, "/api/v2/pokemon?limit=100"))
        return when {
            response.status.successful -> body(response).results
            else -> emptyList()
        }
    }
}

data class Pokemon(val name: String)

data class Results(val results: List<Pokemon>)