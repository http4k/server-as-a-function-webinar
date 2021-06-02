package com.example

import org.http4k.core.Body
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind

/**
 * The endpoint for finding Pokemon by name with prefix. Note that this returns an HttpHandler
 * type which means it can be tested independently or as part of the entire app.s
 */
fun FindAllWithPrefix(
    pokemonClient: PokemonClient
): RoutingHttpHandler = "/{prefix}" bind Method.GET to { req: Request ->
    val resultLens = Body.auto<PokemonList>().toLens()
    val prefixLens = Path.of("prefix")
    val results = pokemonClient.list()
    val pokemonList = PokemonList(
        results.map { it.name }.filter { it.startsWith(prefixLens(req)) }
    )

    Response(Status.OK).with(resultLens of pokemonList)
}

data class PokemonList(val pokemon: List<String>)
