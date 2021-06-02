import com.example.FakePokemonApi
import com.example.Pokemon4k
import org.http4k.core.Method.GET
import org.http4k.core.Request
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse

fun main() {
    val p4k = PrintRequestAndResponse()
        .then(Pokemon4k(FakePokemonApi()))

    p4k(Request(GET, "/pi"))
}