# Nexus Nook

This project is to demonstrate a large database of video games in a native kotlin android app.

## Prerequisites

-  This app uses a public API, [RAWG](https://rawg.io/apidocs) , for the video games database. Please follow their instructions to retrieve an API key to test the app.

-  The RAWG API key should be added under the ```gradle.properties``` file as shown below
```
API_KEY="api_key_here"
```
- Minimum supported SDK version is 24 (Nougat)

>#### NOTE: Please be advised that the project is still under the development, some features may not work as intended and not all functionalities have been implemented yet.

## Technical Details

### Packages & Directory Structure

This project does its best to follow clean architecture and single responsibility. The following section briefly describes the packages.

#### Android Dependent Packages

-  app: Entry point of the application. Only shows the splash screen and initializes some dependencies that will be shared by the rest of the packages such as dependency injection.
-  base: This package contains most commonly shared components, util classes and parent class definitions. Networking component is also here.
-  uicomponent: Custom designed views, styles, themes lay under here.
-  main: The package that contains the real implementation. Whole logic is implemented in this package. It has a single activity as an entry point, rest is handled by fragments.

#### Library Packages

-  buildSrc: Contains config files such as ```Dependencies.kt```, ```Versions.kt``` that are used by ```build.gradle``` files.
-  data: Serializable data classes for REST API are here, also repository classes that call networking components lay under this package.
-  domain: Bridge between ```data``` and ```main```, contains usecases, calls web api through repositories and returns results to the corresponding view.
-  common-model: Commonly shared non-android dependent classes are here.

### Architecture

This project utilizes ```Single Activity``` architecture with ```MVVM``` Moreover, uses ```Repository - Use Case``` pattern for fetching and delivering data.


### Libraries & Techs

-  [AndroidX](https://developer.android.com/jetpack/androidx)
-  [ViewModel](https://developer.android.com/reference/androidx/lifecycle/ViewModel): Architecture, MVVM
-  [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData): Responsive UI
-  [ViewBinding - DataBinding](https://developer.android.com/topic/libraries/data-binding): Binding data sources to view layouts
-  [Coil](https://coil-kt.github.io/coil/): Image loading
-  [Koin](https://insert-koin.io/): Dependency Injection
-  [Ktor](https://ktor.io/): REST API
-  [Timber](https://github.com/JakeWharton/timber): Logging
-  [GSON](https://github.com/google/gson): Json conversions
-  [ExoPlayer](https://github.com/google/ExoPlayer): Video Player


## In-App Screenshots


<img src="https://raw.githubusercontent.com/YKakdas/ImagesForReadme/main/Homepage_1.jpg?raw=true" width="200" height=420> <img src="https://raw.githubusercontent.com/YKakdas/ImagesForReadme/main/Homepage_2.jpg?raw=true" width="200" height=420> <img src="https://raw.githubusercontent.com/YKakdas/ImagesForReadme/main/Homepage_3.jpg?raw=true" width="200" height=420> 

<img src="https://raw.githubusercontent.com/YKakdas/ImagesForReadme/main/GameDetails_2.jpg?raw=true" width="200" height=420> <img src="https://raw.githubusercontent.com/YKakdas/ImagesForReadme/main/DetailPage_1.jpg?raw=true" width="200" height=420>

## GIFs

<img src="https://github.com/YKakdas/ImagesForReadme/blob/c25e2b17edb7bf06527c7e185ab47010f42e6448/dashboard_1.gif"  width="240" height=480>  <img src="https://github.com/YKakdas/ImagesForReadme/blob/c25e2b17edb7bf06527c7e185ab47010f42e6448/dashboard_2.gif"  width="240" height=480>


<img src="https://github.com/YKakdas/ImagesForReadme/blob/c25e2b17edb7bf06527c7e185ab47010f42e6448/detail_page_1.gif"  width="240" height=480>  <img src="https://github.com/YKakdas/ImagesForReadme/blob/c25e2b17edb7bf06527c7e185ab47010f42e6448/detail_page_2.gif"  width="240" height=480>


#### Yasar Kakdas
