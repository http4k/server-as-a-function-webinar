package com.example

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.Credentials
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.security.InsecureCookieBasedOAuthPersistence
import org.http4k.security.OAuthProvider
import org.http4k.security.gitHub

/**
 * For OAuth, we need to pick up some variables from the environment, and to construct the OAuthProvider
 * which supplies the HTTP callback and security filters for securing the app.
 */
fun Pokemon4k(
    pokemonHttp: HttpHandler = RealPokemonApi(),
    gitHubHttp: HttpHandler = RealGitHub(),
    env: Environment = Environment.ENV
): HttpHandler {
    val clientId = EnvironmentKey.required("CLIENT_ID")
    val clientSecret = EnvironmentKey.required("CLIENT_SECRET")

    // http4k-security-oauth is bundled with a set of popular auth services, or you can configure your own!
    val oAuthProvider = OAuthProvider.gitHub(
        gitHubHttp,
        Credentials(clientId(env), clientSecret(env)),
        Uri.of("http://localhost:8000/oauth/callback"),
        InsecureCookieBasedOAuthPersistence("cookie"),
    )

    val pokemonClient = PokemonClient(pokemonHttp)

    return Debug()
        .then(
            routes(
                "/oauth/callback" bind GET to oAuthProvider.callback,
                oAuthProvider.authFilter.then(FindAllWithPrefix(pokemonClient))
            )
        )
}