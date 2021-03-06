package com.example

import org.http4k.core.Body
import org.http4k.core.ContentType
import org.http4k.core.ContentType.Companion.TEXT_HTML
import org.http4k.core.Request
import org.http4k.lens.Header.ACCEPT
import org.http4k.template.HandlebarsTemplates
import org.http4k.template.TemplateRenderer
import org.http4k.template.viewModel

class SelectingViewModelRenderers(rendererFn: HandlebarsTemplates.() -> TemplateRenderer) {
    private val htmlRenderer = rendererFn(HandlebarsTemplates {
        it.apply { loader.suffix = ".html" }
    })
    val turboRenderer = rendererFn(HandlebarsTemplates { it.apply { loader.suffix = ".turbo-stream.html" } })

    /**
     * Select the correct renderer for the request
     */
    operator fun invoke(request: Request) =
        when {
            ACCEPT(request)?.accepts(ContentType.TURBO_STREAM) == true -> Body.viewModel(
                turboRenderer,
                ContentType.TURBO_STREAM
            ).toLens()
            else -> Body.viewModel(htmlRenderer, TEXT_HTML).toLens()
        }
}

val ContentType.Companion.TURBO_STREAM get() = ContentType("text/vnd.turbo-stream.html")
