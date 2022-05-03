# IMDB TV Show Browser
This is app is a simple TvShow browser with a list and a detail view. The list has an infinite scroll using the android Paging library. The detail view has some info on the selected TvShow as well as a lateral swipe feature that will allow you to navigate between shows similar to the selected one.
## Main Dependencies
* [TMBD REST API](https://developers.themoviedb.org/3/getting-started/introduction) 
* [Kotlin](https://kotlinlang.org/docs/android-overview.html)
* [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel)
* [DataBinding](https://developer.android.com/topic/libraries/data-binding)
* [Coroutines](https://developer.android.com/kotlin/coroutines)
* [Flow](https://developer.android.com/kotlin/flow)
* [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started)
* [ViewPager2](https://developer.android.com/guide/navigation/navigation-swipe-view-2)
* [Retrofit](https://square.github.io/retrofit/)
* [Room](https://developer.android.com/training/data-storage/room)
* [MotionLayout](https://developer.android.com/training/constraint-layout/motionlayout?hl=es-419)
* [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
* [Koin](https://insert-koin.io/)
## Getting started
For this app to work you must add your own [TMDB](https://www.themoviedb.org/) API key in the [api_key.xml](https://github.com/orxeira/tmdb-browser/blob/main/app/src/main/res/values/api_key.xml) file.

```
<resources>
    <string name="api_key">INSERT_YOUR_TMDB_API_KEY_HERE</string>
</resources>
```

## Known issues:
* Poster hiding animation in DetailFragment is not working as intended
