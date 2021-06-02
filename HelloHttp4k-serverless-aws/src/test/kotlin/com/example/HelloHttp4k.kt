package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.server.SunHttp
import org.http4k.server.asServer

fun main() {
    val http = FakePokemonApi()
    val server = Pokemon4k(http).asServer(SunHttp(8000)).start()
    val appClient = JavaHttpClient()
    appClient(Request(GET, "http://localhost:8000/b"))
}
