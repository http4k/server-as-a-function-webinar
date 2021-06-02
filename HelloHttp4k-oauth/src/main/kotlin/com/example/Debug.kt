package com.example

import org.http4k.core.Filter

fun Debug() = Filter { next ->
    { request ->
        next(request).also { response -> println(response) }
    }
}