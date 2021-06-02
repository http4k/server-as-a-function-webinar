package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.Environment.Companion.ENV
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
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun SecuredPokemon4kApi(env: Environment = ENV): HttpHandler {
    val clientId = EnvironmentKey.required("CLIENT_ID")
    val clientSecret = EnvironmentKey.required("CLIENT_SECRET")

    val oAuthProvider = OAuthProvider.gitHub(
        JavaHttpClient(),
        Credentials(clientId(env), clientSecret(env)),
        Uri.of("http://localhost:8000/oauth/callback"),
        InsecureCookieBasedOAuthPersistence("cookie"),
    )

    return routes(
        "/oauth/callback" bind GET to oAuthProvider.callback,
        oAuthProvider.authFilter.then(Pokemon4kApi())
    )
}

fun main() {
    SecuredPokemon4kApi().asServer(SunHttp(8000)).start()
}