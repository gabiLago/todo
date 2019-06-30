#  TODO


![Swift Version](https://img.shields.io/badge/kotlin-1.3.31-green.svg)

## DESCRIPTION
### TODO is an app for educatonal purposes.

### It uses Kotlin Coroutines with LiveData, Room for database and Koin for dependencies injection.
### It basically allows to note down and track todo tasks.
### Task items will have a content and a date. It can be set as prioritary and as finished.
### Items can be added, edited and deleted.

### *First time using git-flow as part of the exercise*.


## NOTES ON THE EXERCISE
This is an exercice for the 8th Mobile Development Bootcamp from KeepCoding.

1. **Add missing fields**
	* Done. Card now has fields for **content**,  **date**, **isFinished** and **isHighPriority** 
2. **Develop activity detail**
	* Done​. Items on RecyclerView are clickable, landing on a Detail View that also has buttons for editing and deleting the Task.
3. **Add an action to delete a task**
	* a. Done
	* b. Done
4. **Añadir una acción de editar una tarea**
	* a. Done
	* b. Done	 


## DOUBTS AND CONCERNS

1. There is a buggy behaviour on the RecylcerView when a Task is added. Coming from the AddTask View, the new card doubles the distance from the previous card. Once the view is reloaded distances became normal. Is it the itemDecoration being called twice on adding the task?

2. I´ve created a singleton for the strikesThrough effect for onFinised task and to show/hide isHighPriority task. Not sure if this a good practice???

3. The way I use onClickListener on the adapter is the only one I got it working. But I´m not quite comfortable with it... at least it looks verbose and ugly... but couldn´t find a better approach

4. When an item is deleted from the list, the whole list is refreshed. I tried to delete from the adapter just the item, but couldn´t make it. Not sure how the list where and item position should be deleted is being sent from fragment to adapter.

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
    implementation 'com.android.support:design:28.0.0'
``` 
