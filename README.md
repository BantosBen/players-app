# MVVM Architecture, Room Database, Retrofit and RxJava

This is a sample app that implements the above mentioned technologies. The App has an authentcation features where the user has to create and sign in to his/her account. 
</br> </br>
Then within the app requests are made to fetch and manipulate data on the server via a rest api prepared. Find the rest api [Players-App-API](https://github.com/BantosBen/players-rest-api).
</br> </br>
A session token is sent to the server to confirm the authorozation of the agent accessing the service. A request cannot be processed when the authorization failed. Moreover the user gets kicked out of the session by signing him/her out.
</br> </br>
## Libraries

### Rest API

* [Rest API](https://github.com/BantosBen/players-rest-api) To handle the back end requests from this app. Check it out 

### Android Kotlin

* [DataBinding](https://developer.android.com/topic/libraries/data-binding/) Declaratively bind observable data to UI elements.

* [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) Create a UI that automatically responds to lifecycle events.

* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) Build data objects that notify views when the underlying database changes.

* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) Store UI-related data that isn't destroyed on app rotations. Easily schedule asynchronous tasks for optimal execution.

* [Room](https://developer.android.com/topic/libraries/architecture/room) Access your app's SQLite database with in-app objects and compile-time checks.

* [Paging](https://developer.android.com/topic/libraries/architecture/paging/) Makes it easier for you to load data gradually and gracefully within your app's RecyclerView.

### HTTP

* [Retrofit2](https://github.com/square/retrofit) Type-safe HTTP client for Android and Java by Square, Inc.

* [OkHttp](https://github.com/square/okhttp) An HTTP+HTTP/2 client for Android and Java applications.

### ReactiveX

* [RxJava2](https://github.com/ReactiveX/RxJava) A library for composing asynchronous and event-based programs using observable sequences for the Java VM

* [RxAndroid](https://github.com/ReactiveX/RxAndroid) RxJava bindings for Android
