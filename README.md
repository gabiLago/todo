#  TODO


![Swift Version](https://img.shields.io/badge/kotlin-1.3.31-green.svg)

## DESCRIPTION
### TODO is an app using Kotlin Coroutines with LiveData, Room for database and Koin for depencies injection.
### It basically allows to note down and track todo tasks.
### Task items will have a content and a date. It can be set as prioritary and as finished.
### Items can be added, edited and deleted.


## NOTES ON THE EXERCISE
This is an exercice for the 8th Mobile Development Bootcamp from KeepCoding.

1. **Add missing fields**
	* Done.
2. **Develop activity detail**
	* Done​.
3. **Add an action to delete a task**
	* a. Obligatorio desde la lista
	* b. Done
4. **Añadir una acción de editar una tarea**
	* a. Obligatorio desde la lista
	* b. Done	 
	
## COMMENTS
* I´ve used git-flow to develop the project.

## DEPENDENCIES

```bash
implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.0.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1'
    implementation "androidx.lifecycle:lifecycle-extensions:2.1.0-beta01"
    kapt "androidx.lifecycle:lifecycle-compiler:2.1.0-beta01"

    implementation 'org.koin:koin-android:2.0.1'
    implementation 'org.koin:koin-android-viewmodel:2.0.1'

    implementation "androidx.room:room-ktx:2.1.0-rc01"
    kapt "androidx.room:room-compiler:2.1.0-rc01"
    implementation 'com.jakewharton.threetenabp:threetenabp:1.2.1'
    implementation 'com.github.clans:fab:1.6.4'

    testImplementation 'junit:junit:4.12'
    testImplementation "org.mockito:mockito-core:2.+"
    testImplementation "com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0"
    testImplementation "androidx.arch.core:core-testing:2.1.0-beta01"
    testImplementation 'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1'
    androidTestImplementation 'androidx.test:runner:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.0'
    implementation 'androidx.recyclerview:recyclerview:1.0.0'
    implementation 'androidx.cardview:cardview:1.0.0'
``` 
