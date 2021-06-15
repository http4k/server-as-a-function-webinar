package com.example

import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val http = FakePokemonApi()
    val server = Pokemon4k(http).asServer(Undertow(8001)).start()
}
