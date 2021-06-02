package com.example

import org.http4k.core.Filter

/**
 * Custom debug handler to print our Responses. Use this or the built-in
 * DebuggingFilters.PrintRequestAndResponse()
 */
fun Debug(printFn: (String) -> Unit = ::println) = Filter { next ->
    { request ->
        next(request).also { response -> printFn(response.toString()) }
    }
}