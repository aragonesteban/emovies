<h1 align="center">eMovies</h1>

<h3 align="center">
Movies App based in www.themoviedb.org
</h3>

<div align="center">
<img src="/previews/home.png" width="300"/>
    
<img src="/previews/detail.png" width="300"/>
   
<img src="/previews/trailer.png" width="300"/>
</div>


## Tech stack
- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose) Android’s modern toolkit for building native UI. (The app was built using JetpackCompose and XML for create interfaces to give a demonstration of how we can inject new way to create interfaces with old way to create interfaces).
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection.
- Jetpack
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - Observe Android lifecycles and handle UI states upon the lifecycle changes.
    - [View Binding](https://developer.android.com/topic/libraries/view-binding) - Feature that allows you to more easily write code that interacts with views.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Manages UI-related data holder and lifecycle aware. Allows data to survive configuration changes such as screen rotations.
    - [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Handling and manage the navigation in the app.
    - [Room Persistence](https://developer.android.com/jetpack/androidx/releases/room) - Constructs Database by providing an abstraction layer over SQLite to allow fluent database access.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - Construct the REST APIs.
- [Kotlin Serialization](https://kotlinlang.org/docs/serialization.html) - A modern JSON library for Kotlin.
- [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow) - state-holder observable flow that emits the current and new state updates to its collectors.
- [Coroutines](https://developer.android.com/kotlin/coroutines) - for asynchronous calls.
- [Coil](https://coil-kt.github.io/coil/) - for loading images from network.
- [MockK](https://mockk.io/) - Library for unit testing based in kotlin.
- [RefreshVersion](https://github.com/jmfayard/refreshVersions) - to manage dependencies version from kotlinDSL.

<br />

## Architecture
**eMovies** is based on the MVVM architecture and the Repository pattern and it has the approach in Clean Architecture where we can manage different layers.
This architecture contains three main layers:

- **data**: In this module/layer it will be everything about datasources like network and cache
- **domain**: In this module lives everything about business logic working with use cases, also is the responsible to get data from data module to send it to the view.
- **features**: In this module lives all presentation

<br />

## How I run the app?
- Clone the repository
- Open it in Android Studio
- Wait until dependencies are installed
- Run app in your emulator or physical device

If you don't have Android Studio and you don't want to clone the repository, you can downlad an apk [here](https://www.mediafire.com/file/si1kpvvwgbsn8eg/eMovies.apk/file) 


## Questions
1. ¿En qué consiste el principio de responsabilidad única? ¿Cuál es su propósito? 
 - Este principio se basa básicamente en que un objeto o función debe hacer una única cosa, esto con el propósito de separar responsabilidades y tener un orden en qué hace cada cosa.
2. ¿Qué características tiene, según su opinión, un “buen” código o código limpio? 
 - En mi concepto un bueno código o código limpio es aquel que habla por si solo, es entendible, escalable y perdura en el tiempo.
3. Detalla cómo harías todo aquello que no hayas llegado a completar. 
 - No agregue muchas animaciones como lo esperaba, tal vez haría algo con MotionLayout en el detalle de la pelicula cuando se muestra o aún mejor, usuaria JetpackCompose para gestionar estas animaciones en este modulo de la app. 
