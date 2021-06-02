package com.example

import org.http4k.client.JavaHttpClient
import org.http4k.core.Uri
import org.http4k.core.then
import org.http4k.filter.ClientFilters

fun RealGitHub() = ClientFilters.SetBaseUriFrom(Uri.of("https://github.com"))
    .then(JavaHttpClient())