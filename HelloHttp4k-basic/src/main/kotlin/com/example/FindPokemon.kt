package com.example

import org.http4k.core.Body
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.format.Moshi.auto
import org.http4k.lens.Path
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import java.time.Clock
import java.time.Instant

fun findPokemon(pokemonApi: PokemonApi, clock: Clock): RoutingHttpHandler {

    val prefix = Path.of("prefix")

    val body = Body.auto<ListView>().toLens()

    return "/{prefix}" bind GET to { req: Request ->
        Response(OK).with(body of
            ListView(
                clock.instant(),
                pokemonApi.list()
                    .filter { it.name.startsWith(prefix(req), true) }
                    .map { it.name }
            )
        )
    }
}

data class ListView(val time: Instant, val pokes: List<String>)
