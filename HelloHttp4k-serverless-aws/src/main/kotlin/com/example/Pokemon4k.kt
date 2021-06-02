@file:Suppress("unused")

package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction

fun Pokemon4k(pokeCoApi: HttpHandler = RealPokemonApi()): HttpHandler {
    val pokemonClient = PokemonClient(pokeCoApi)

    return Debug().then(
        routes(FindAllWithPrefix(pokemonClient))
    )
}

/**
 * This class is the AWS StreamHandler which adapts to our HttpHandler. Note that
 * http4k uses Moshi (light!) instead of Jackson (heavy!) for JSON manipulation.
 */
class Pokemon4kLambda : ApiGatewayV1LambdaFunction(Pokemon4k())
