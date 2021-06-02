package com.example

import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    Pokemon4k().asServer(Undertow(8000)).start()
}

