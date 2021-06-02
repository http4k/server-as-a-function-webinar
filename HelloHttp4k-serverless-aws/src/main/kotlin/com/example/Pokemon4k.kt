package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.ResponseFilters.ReportHttpTransaction
import org.http4k.filter.ServerFilters.CatchLensFailure
import org.http4k.routing.routes
import org.http4k.serverless.ApiGatewayV1LambdaFunction
import java.time.Clock

fun Pokemon4k(http: HttpHandler = JavaHttpClient(), clock: Clock = Clock.systemUTC()): HttpHandler {
    val pokemonApi = PokemonApi(http)

    return CatchLensFailure()
        .then(ReportHttpTransaction {
            println("""${it.request.uri} returned ${it.response.status}""")
        })
        .then(
            routes(findPokemon(pokemonApi, clock))
        )
}

@Suppress("unused")
class Pokemon4kLambda : ApiGatewayV1LambdaFunction(Pokemon4k())
