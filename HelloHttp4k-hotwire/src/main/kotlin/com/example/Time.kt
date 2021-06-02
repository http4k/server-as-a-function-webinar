package com.example

import org.http4k.routing.RoutingSseHandler
import org.http4k.routing.bind
import org.http4k.sse.Sse
import org.http4k.sse.SseMessage
import org.http4k.template.TemplateRenderer
import org.http4k.template.ViewModel
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle.MEDIUM
import java.util.concurrent.Executors.newSingleThreadScheduledExecutor
import java.util.concurrent.TimeUnit.MILLISECONDS

fun time(renderer: TemplateRenderer): RoutingSseHandler {
    val executor = newSingleThreadScheduledExecutor()

    return "/time" bind { sse: Sse ->
        executor.scheduleWithFixedDelay({
            sse.send(SseMessage.Data(renderer(Time(now()))))
        }, 0, 1000, MILLISECONDS)
    }
}

class Time(raw: LocalDateTime) : ViewModel {
    val time = raw.format(DateTimeFormatter.ofLocalizedDateTime(MEDIUM))
}
