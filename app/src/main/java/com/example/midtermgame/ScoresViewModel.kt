package com.example.midtermgame

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

public suspend fun <T> LiveData<T>.await(): T {
    // A utility function to await a LiveData value using coroutines.
    return withContext(Dispatchers.Main.immediate) {
        suspendCancellableCoroutine { continuation ->
            // Execute the following code in the coroutine context.

            val observer = object : Observer<T> {
                override fun onChanged(value: T) {
                    // When the LiveData value changes...
                    removeObserver(this) // Remove the observer to avoid memory leaks.
                    continuation.resume(value) // Resume the coroutine with the value.
                }
            }
            observeForever(observer) // Observe LiveData changes forever.

            // If the coroutine is cancelled, remove the observer to avoid memory leaks.
            continuation.invokeOnCancellation {
                removeObserver(observer)
            }
        }
    }
}

class ScoresViewModel(val dao: ScoresDao) : ViewModel() {

    var newUserName = "Andrew"
    var newUserScore = 0

    val scores = dao.getAll()
    // LiveData of a list of scores retrieved from the DAO.

    fun addHighScore() {
        // Function to add a new score to the database.
        viewModelScope.launch {
            // Launch a coroutine in the ViewModel's scope.

            val scores = Score()
            scores.userName = newUserName
            scores.userScore = newUserScore

            val id = dao.insert(scores) // Insert the note and get its ID.
        }
    }

    fun deleteNote(noteId: Long) {
        // Function to delete a note from the database.
        viewModelScope.launch {
            // Launch a coroutine in the ViewModel's scope.

            val scores = dao.get(noteId).await()
            // Suspend until the LiveData has a value, then get the note.

            dao.delete(scores) // Delete the note from the database.
        }
    }
}
