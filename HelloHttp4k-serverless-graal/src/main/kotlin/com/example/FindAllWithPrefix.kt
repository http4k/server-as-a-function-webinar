package com.example

import com.example.PokemonMoshi.auto
import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.lens.Path
import org.http4k.routing.bind
import se.ansman.kotshi.JsonSerializable

fun FindAllWithPrefix(
    pokemonClient: PokemonClient
) = "/{prefix}" bind Method.GET to { req: Request ->
    val resultLens = Body.auto<PokemonList>().toLens()
    val prefixLens = Path.of("prefix")
    val results = pokemonClient.list()
    val pokemonList = PokemonList(
        results.map { it.name }.filter { it.startsWith(prefixLens(req)) }
    )

    Response(Status.OK).with(resultLens of pokemonList)
}

@JsonSerializable
data class PokemonList(val pokemon: List<String>)
