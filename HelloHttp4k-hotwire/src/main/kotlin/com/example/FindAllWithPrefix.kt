package com.example

import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.routing.RoutingHttpHandler
import org.http4k.routing.bind
import org.http4k.template.ViewModel

private var startingChar = 'a'

/**
 * A slightly modified version of this endpoint returns new TurboStream content on every call.
 */
fun findAllWithPrefix(
    renderers: SelectingViewModelRenderers,
    pokemonClient: PokemonClient
): RoutingHttpHandler = "/find" bind POST to { req: Request ->
    val model = PokemonList(startingChar.toString(),
        pokemonClient.list()
            .map { it.name }
            .filter { it.startsWith(startingChar) }
            .map { it.capitalize() }
    )

    Response(OK).with(renderers(req) of model).also { startingChar++ }
}

data class PokemonList(val prefix: String, val results: List<String>) : ViewModel

