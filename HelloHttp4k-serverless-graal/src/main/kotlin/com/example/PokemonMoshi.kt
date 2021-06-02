package com.example

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import org.http4k.format.CollectionEdgeCasesAdapter
import org.http4k.format.ConfigurableMoshi
import org.http4k.format.asConfigurable
import org.http4k.format.withStandardMappings
import se.ansman.kotshi.KotshiJsonAdapterFactory

object PokemonMoshi : ConfigurableMoshi(
    Moshi.Builder()
    .add(KotshiPokemonJsonAdapterFactory)
    .addLast(CollectionEdgeCasesAdapter)
    .asConfigurable()
    .withStandardMappings()
    .done())


@KotshiJsonAdapterFactory
abstract class PokemonJsonAdapterFactory : JsonAdapter.Factory
