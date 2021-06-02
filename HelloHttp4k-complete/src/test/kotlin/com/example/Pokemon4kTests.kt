package com.example

import com.natpryce.hamkrest.assertion.assertThat
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.hamkrest.hasBody
import org.http4k.server.Http4kServer
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import org.http4k.testing.Approver
import org.http4k.testing.JsonApprovalTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Pokemon4k is just an HttpHandler function so we can test it In-Memory. Note that there is no
 * custom infrastructure setup required for any of these tests.
 */
class Pokemon4kTest {
    @Test
    fun `find all pokemon with prefix`() {
        val http = Pokemon4k(FakePokemonApi())
        val response = http(Request(GET, "/b"))

        assertThat(
            response, hasBody(
                """{"pokemon":["bulbasaur","blastoise","butterfree","beedrill","bellsprout"]}"""
            )
        )
    }
}

/**
 * Approval tests use an externally saved resource which responses can be compared against. Here,
 * we're using the JSON version
 */
@ExtendWith(JsonApprovalTest::class)
class Pokemon4k_ApprovalTest {
    @Test
    fun `find all pokemon with prefix`(approver: Approver) {
        val http = Pokemon4k(FakePokemonApi())
        val response = http(Request(GET, "/b"))

        // the injected approver checks the response JSON against the approved file
        approver.assertApproved(response)
    }
}

/**
 * We can also test the server by starting it up on a random port.
 */
class Pokemon4kServerTest {

    private val server: Http4kServer = Pokemon4k(FakePokemonApi()).asServer(SunHttp(0))

    @BeforeEach
    fun start() {
        server.start()
    }

    @AfterEach
    fun stop() {
        server.stop()
    }

    @Test
    fun `find all pokemon with prefix`() {
        val http = JavaHttpClient()
        val response = http(Request(GET, "http://localhost:${server.port()}/b"))

        assertThat(
            response, hasBody(
                """{"pokemon":["bulbasaur","blastoise","butterfree","beedrill","bellsprout"]}"""
            )
        )
    }
}