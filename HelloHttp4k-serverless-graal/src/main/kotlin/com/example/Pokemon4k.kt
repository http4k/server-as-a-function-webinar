package com.example

import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1FnLoader
import org.http4k.serverless.AwsLambdaRuntime
import org.http4k.serverless.asServer

fun Pokemon4k(pokeCoApi: HttpHandler = RealPokemonApi()): HttpHandler {
    val pokemonClient = PokemonClient(pokeCoApi)

    return Debug().then(
        routes(FindAllWithPrefix(pokemonClient))
    )
}

/**
 * GraalVM uses a custom http4k AWS Runtime with zero reflection.
 */
fun main() {
    ApiGatewayV1FnLoader(Pokemon4k()).asServer(AwsLambdaRuntime()).start()
}