package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

/**
 * Our app. The pokeCoApi HttpHandler is injected so we can use the Fake or the Real version.
 * Notice that the app is decoupled from the server launching below, which means we can trivially
 * test it in-memory.
 */
fun Pokemon4k(pokeCoApi: HttpHandler = RealPokemonApi()): HttpHandler {
    val pokemonClient = PokemonClient(pokeCoApi)

    return Debug().then(
        routes(FindAllWithPrefix(pokemonClient))
    )
}

/**
 * Launches the server on a port
 */
fun main() {
    Pokemon4k().asServer(Undertow(8000)).start()
}