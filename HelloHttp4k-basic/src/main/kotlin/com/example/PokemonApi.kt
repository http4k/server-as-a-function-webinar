package com.example

import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import org.http4k.format.Moshi.auto

class PokemonApi(http: HttpHandler) {

    private val http = SetBaseUriFrom(Uri.of("https://pokeapi.co/")).then(http)

    private val body = Body.auto<Results>().toLens()

    fun list() = body(http(Request(GET, "/api/v2/pokemon").query("limit", "100"))).results
}

data class Pokemon(
    val name: String,
    val url: Uri
)

data class Results(
    val results: List<Pokemon>
)
