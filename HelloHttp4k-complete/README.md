# HelloHttp4k-complete

This module contains the completed code for the Pokémon application with tests and commentary on the code. Testing styles used:

- Unit (in-memory) for Filters, Endpoint and App levels.
- Server (random port bound) - App level.
- Approval (in-memory) - compares responses against VCS controlled versions.
- Contracts (in-memory/live) for Fake and Real implementations of the `PokemonAPI` client to ensure they match.

## Run the app from the IDE:
Main class: `com.example.Http4kKt`
