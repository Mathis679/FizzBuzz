# FizzBuzz

Create FizzBuzz app based on [FizzBuzz problem](https://en.wikipedia.org/wiki/Fizz_buzz)

## Architecture

Modularized kotlin android application based on [Android Developers architecture guidelines](https://developer.android.com/topic/architecture?hl=fr) :

### Dependency injection

Dependency injection performed with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android?hl=fr)

### UI Design Pattern

Chosen UI design pattern is [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel?hl=fr) to fit last requirements from Android and recommended pattern.

### Gradle

Separated file to declare all libs and versions in `dependencies.gradle`

Every sub-module (excepts `:app`) applied config from `base.gradle`

## UI

I decided to do UI part with [Jetpack Compose](https://developer.android.com/jetpack/compose?hl=fr) because it offers powerful tools to implement beautiful UI with less and comprehensive code.

### Material3

Theme is designed with [Material3](https://m3.material.io) lib

### Paging

[Paging lib](https://developer.android.com/topic/libraries/architecture/paging/v3-overview?hl=fr) is used to allow users have infinite fizz buzz list.

## Tests

### Mockk

[Mockk](https://mockk.io) to perform kotlin unit test mocking.

### Robolectric

[Robolectric](https://robolectric.org) for test runner when need instrumented app context.

### JUnit

[JUnit](https://junit.org/junit4/) to run tests.
