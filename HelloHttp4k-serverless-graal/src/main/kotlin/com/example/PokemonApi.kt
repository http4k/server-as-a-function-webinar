package com.example

import com.example.PokemonMoshi.auto
import org.http4k.core.Body
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters.SetBaseUriFrom
import se.ansman.kotshi.JsonSerializable

class PokemonApi(http: HttpHandler) {

    private val http = SetBaseUriFrom(Uri.of("https://pokeapi.co/")).then(http)

    private val body = Body.auto<Results>().toLens()

    fun list() = body(http(Request(GET, "/api/v2/pokemon").query("limit", "100"))).results
}

@JsonSerializable
data class Pokemon(
    val name: String,
    val url: Uri
)

@JsonSerializable
data class Results(
    val results: List<Pokemon>
)
