package com.example

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.routing.bind

fun FindAllWithPrefix(
    pokemonClient: PokemonClient
) = "/{prefix}" bind GET to { req: Request ->
    val resultLens = Body.auto<PokemonList>().toLens()
    val prefixLens = Path.of("prefix")
    val results = pokemonClient.list()
    val pokemonList = PokemonList(
        results.map { it.name }.filter { it.startsWith(prefixLens(req)) }
    )

    Response(OK).with(resultLens of pokemonList)
}

data class PokemonList(val pokemon: List<String>)
