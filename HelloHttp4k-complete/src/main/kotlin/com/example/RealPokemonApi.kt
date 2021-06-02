package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

/**
 * A simple Http client which pre-sets the base URL for the Pokemon API
 */
fun RealPokemonApi(): HttpHandler = ClientFilters.SetBaseUriFrom(Uri.of("https://pokeapi.co"))
    .then(JavaHttpClient())