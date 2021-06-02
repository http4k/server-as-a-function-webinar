package com.example

import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.routing.bind
import org.http4k.template.ViewModel

private var startingChar = 'a'

fun findPokemon(
    renderers: SelectingViewModelRenderers,
    pokemonApi: PokemonApi
) = "/find" bind POST to { req: Request ->
    val pokemon = pokemonApi.list()
        .filter { it.name.startsWith(startingChar, true) }
        .map { it.name.capitalize() }

    val model = ListView(startingChar.toUpperCase(), pokemon)

    Response(OK).with(renderers(req) of model).also { startingChar++ }
}

data class ListView(val char: Char, val pokemon: List<String>) : ViewModel
