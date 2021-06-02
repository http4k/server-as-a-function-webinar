package com.example

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.http4k.core.HttpHandler
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.core.then
import org.junit.jupiter.api.Test

/**
 * Simple test for a filter.
 */
class DebugTest {

    @Test
    fun `prints response`() {
        val suppliedResponse = Response(I_M_A_TEAPOT).body("hello")

        var content = ""

        val filter = Debug { content = it }

        val endpointHandler: HttpHandler = { req: Request -> suppliedResponse }
        val decoratedHandler: HttpHandler = filter.then(endpointHandler)

        val actualResponse = decoratedHandler(Request(GET, ""))

        assertThat(actualResponse, equalTo(suppliedResponse))
        assertThat(content, equalTo(suppliedResponse.toString()))
    }
}