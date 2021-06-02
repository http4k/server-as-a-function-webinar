package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

fun RealPokemonApi() = ClientFilters.SetBaseUriFrom(Uri.of("https://pokeapi.co"))
    .then(JavaHttpClient())