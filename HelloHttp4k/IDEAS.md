### Section Notes

## Slides - about, Introduce HttpHandler, Filter, detached runtime, modules (here or at end?)
- Use Toolbox to generate project - server + moshi, CHANGE NAME! Gradle, ZIP
- Show preview - gradle file with modules
- Download it
- Unzip it

### Generated starter project
- What we’re going to do
  - Write a client to the pokemon API
  - Put our own API on top of it
  - Transform the data
  - Test it
  - Write a fake implementation
  - Do it inside out!

1. Download a default template project from the Toolbox
2. Extract into folder
3. Explain what we've got - delete all the content
4. Create a test with a real server to talk to the pokemon API at: https://pokeapi.co/api/v2/pokemon?limit=100

- System out the contents

5. Demonstrate putting filters (setbase url + debugging filter) into the chain
6. Extract this into a function
7. Start it on a server
8. Add new client to talk to http://localhost:8000
9. Grab output and put it into the toolbox -> replace with HttpHandler and hardcoded response
10. Make another test which just uses the in memory FakePokemonAPI
11. Extract out the FakePokemon API into our PokemonApp, add routing

- Add routing
- Add Moshi dependency
- Take JSON, put it into the toolbox, generate model, remove bits we don’t want
- Replace string Uri with Uri type - show how the custom mapping is done in Jackson
- Write a system test for our new API
- New routing
- Get all pokemon starting with a letter /pokemon/a
- Convert it so we just return the names
- Write our own model
- Put the entire thing into AWS Lambda

## Slides - Introduce AppLoader, FnLoader, FnHandler, FnFilter

### Serverless version of the app (AWS)
- Show adding http4k-serverless-lambda
- Entire app is unchanged
- Show `Pokemon4kLambda` extending built in class - also V1/2/Rest/Event functions 
- These are all StreamRequestHandlers 
- using Moshi and not Jackson
- Package via ZIP
- Intro Pulumi
- Deploy it
- Show that ZIP is only 5.65Mb
- Call it (via browser)
- Call it again (via browser)
- Show startup time and memory difference

### Serverless version of the app (GraalVM)
- Show adding http4k-serverless-lambda-runtime
- Custom built Runtime for http4k - lightweight!
- Show `Pokemon4kLambda` using AwsLambdaRuntime and FnLoader
- Package via ShadowJAR and native docker build
- Avoid reflection so use Kapt to generate adapters 
- JsonSerializable - custom Moshi instant
- Deploy it
- Show that ZIP is only 12Mb - includes runtime
- Call it (via browser)
- Call it again (via browser)
- Show startup time and memory difference

### Adding OAuth with GitHub
- Show adding http4k-security-oauth - lightweight - 280k
- Simple built in OAuth via Auth0, GitHub, Google and others
- No reflection again - Moshi
- Show filter and callback, OAuthPersistence
- Demo it with GitHub
- Comes with testing server for local

## Slides - Introduce RealTime: Sse, SseFilter

### Serving Hotwire and SSE
- live-Server rendered HTML/SSE technology
- Turbo-stream content type - reusable templates
- show PolyHandler, Time (SSE), Undertow
- Demo 
- Clock is SSE
- Load Pokemon via button click
- SSE (and Websockets) are unit testable
