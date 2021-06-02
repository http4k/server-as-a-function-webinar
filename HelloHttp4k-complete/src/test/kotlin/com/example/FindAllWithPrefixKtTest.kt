package com.example

import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.hamkrest.hasBody
import org.junit.jupiter.api.Test

/**
 * FindAllWithPrefix returns an HttpHandler, so we can test it independently.
 */
class FindAllWithPrefixTest {

    @Test
    fun `find all pokemon with prefix`() {
        val http = FindAllWithPrefix(PokemonClient(FakePokemonApi()))
        val response = http(Request(GET, "/b"))

        assertThat(
            response, hasBody(
                """{"pokemon":["bulbasaur","blastoise","butterfree","beedrill","bellsprout"]}"""
            )
        )
    }
}