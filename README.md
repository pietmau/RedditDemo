# Reddit Demo

## How to run
This project should build and run as it is (developed using Android Studio Android Studio Giraffe | 2022.3.1 Patch 1).

Api keys can be found in ```apikey.properties``` (api keys should really not be in Git, they are now, for simplicity).

## What does it do?
This demo fetches 100 posts (limit) from the `pics` subreddit and displays them in a list.

It saves the result via Room and displays it from the database if the network is not available or there is a connection error.

(The database queries 100 posts sorted by post creation date).

When a post is clicked, it opens a detail screen with the post's image and title.

**Pagination must be added!!!** And page size should be dynamic, based on screen size, network conditions, usage patterns, etc...

I also would have been the right thing to do to **show ths saved results from the database while fetching new data** from the network.

### Please note
- Most posts in the `pics` subreddit do not have a text.
I selected this subreddit because it was my idea to run some TensorFlow models on the images (which I did not do, due to time constraints).

- Some images do not load. I did not investigate this.

## How is it built?

### Architecture
Clean architecture + MVVM.

### Auth
Reddit uses Oauth2 for authentication. This demo uses the `installed app` flow.

It is implemented using interceptors.

### Modularization
- Modularization is based on features and layers.

There are different approaches to modularization, such as organizing
by feature or by layer (presentation, domain and data), or using a combination of these, or other methods.
The choice of approach should be made based on the project's specifics.

(This project is modularized more than necessary, for demonstration purposes).

### Libraries
- Dependency injection:  ```Hilt```
- Networking: ```Retrofit```
- Persistence: ```Room```
- Concurrency: ```Coroutines + Flow```
- UI: ```Jetpack Compose```
- Image loading: ```Coil```
- Testing
  - ```Mockk```
  - ```Turbine```
  - ```Google Truth```
  - ```Compose Testing```

### Navigation
Compose Navigation.

### Dependencies management
Gradle Kotlin DSL + version catalogs.

## TODO
- Improve detail page!
- Use encrypted shared preferences (or some other encrypted storage) to store tokens.
- Implement proper logging.
- Add analytics and remote configuration.
- Endpoint urls and relevant constants should be configurable (via remote config or, at least, via Gradle).
- Api keys should not be in Git (they are now, for simplicity).
- **Pagination must be added!!!** And page size should be dynamic, based on screen size, network, usage patterns, etc
- more...

## Tests
Coverage is limited due to time constraints.

### Some unit tests are in:

- data/posts/src/test
- domain/posts/src/test
- feature/posts/src/test
- feature/detail/src/test
- core/network/src/test

### Some UI tests are in?
- feature/posts/src/androidTest
