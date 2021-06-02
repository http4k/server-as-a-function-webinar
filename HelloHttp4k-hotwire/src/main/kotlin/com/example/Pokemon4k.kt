package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ResponseFilters.ReportHttpTransaction
import org.http4k.filter.ServerFilters.CatchAll
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.routes
import org.http4k.routing.sse
import org.http4k.routing.static
import org.http4k.server.PolyHandler
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun Pokemon4k(http: HttpHandler = JavaHttpClient()): PolyHandler {
    val renderers = SelectingViewModelRenderers { CachingClasspath() }

    val pokemonApi = PokemonApi(http)

    return PolyHandler(
        ReportHttpTransaction {
            println("""${it.request.uri} returned ${it.response.status}""")
        }
            .then(CatchAll())
            .then(
                routes(
                    static(Classpath("public")),
                    findPokemon(renderers, pokemonApi),
                    index(renderers)
                )
            ),
        sse = sse(
            time(renderers.turboRenderer)
        )
    )
}

fun main() {
    Pokemon4k().asServer(Undertow(8000)).start()
}
