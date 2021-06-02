package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters
import org.http4k.routing.ResourceLoader.Companion.Classpath
import org.http4k.routing.routes
import org.http4k.routing.sse
import org.http4k.routing.static
import org.http4k.server.PolyHandler

/**
 * This app uses a mixture of HTTP and SSE, so we can simply use a PolyHandler alongside a
 * compatible server backend (Undertow).
 *
 * We also serve static assets and use Handlebars to serve both HTML and TurboStreams content.
 */
fun Pokemon4k(http: HttpHandler = RealPokemonApi()): PolyHandler {
    val renderers = SelectingViewModelRenderers { CachingClasspath() }

    val pokemonApi = PokemonClient(http)

    val httpApp = DebuggingFilters.PrintResponse()
        .then(
            routes(
                static(Classpath("public")),
                findAllWithPrefix(renderers, pokemonApi),
                index(renderers)
            )
        )

    val serverSentEventsApp = sse(time(renderers.turboRenderer))

    return PolyHandler(http = httpApp, sse = serverSentEventsApp)
}
