package com.example

import org.http4k.core.Method.GET
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.with
import org.http4k.routing.bind
import org.http4k.template.ViewModel

fun index(renderers: SelectingViewModelRenderers) =
    "/" bind GET to { Response(OK).with(renderers(it) of Index) }

object Index : ViewModel
