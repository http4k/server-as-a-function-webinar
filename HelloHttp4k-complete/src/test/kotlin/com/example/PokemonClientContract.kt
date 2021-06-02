package com.example

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.hasSize
import com.natpryce.hamkrest.isEmpty
import com.natpryce.hamkrest.throws
import org.http4k.chaos.ChaosBehaviours
import org.http4k.core.HttpHandler
import org.http4k.core.Status.Companion.I_M_A_TEAPOT
import org.http4k.lens.LensFailure
import org.junit.jupiter.api.Test

/**
 * This contract exists to ensure that both the fake and real PokemonApis return the same result.
 *
 * Notice how both the RealPokemonApi and FakePokemonApi are both HttpHandlers so interchangeable.
 *
 * We test here through our abstraction (PokemonClient) so we can test
 *
 * You can find the approved content in the "returns expected body.approved" file
 */
abstract class PokemonClientContract {

    abstract val http: HttpHandler

    val pokemonClient by lazy { PokemonClient(http) }

    @Test
    fun `returns expected body for pokemon`() {
        val list = pokemonClient.list()

        assertThat(list, hasSize(equalTo(100)))
        assertThat(list[0], equalTo(Pokemon("bulbasaur")))
    }
}

/**
 * Test implementation of the contract. We have extra tests here to prove that
 * our behaviour under failure modes using the http4k Chaos module.
 */
class FakePokemonApiTest : PokemonClientContract() {

    override val http = FakePokemonApi()

    @Test
    fun `can cause chaos by blowing up`() {
        http.enableChaos(ChaosBehaviours.ReturnStatus(I_M_A_TEAPOT))

        assertThat(pokemonClient.list(), isEmpty)
    }

    @Test
    fun `can cause chaos by snipping body`() {
        http.enableChaos(ChaosBehaviours.NoBody())

        assertThat({ pokemonClient.list() }, throws<LensFailure>())
    }
}

/**
 * Real implementation of the contract
 */
class RealPokemonApiTest : PokemonClientContract() {
    override val http = RealPokemonApi()
}