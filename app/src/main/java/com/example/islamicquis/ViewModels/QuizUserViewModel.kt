package com.example.islamicquis.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.islamicquis.Repository.QuizRepository
import com.example.islamicquis.Repository.QuizUserRepository
import com.example.islamicquis.data.QuizQuestion
import com.example.islamicquis.data.User
import kotlinx.coroutines.launch

class QuizUserViewModel(private val repository: QuizUserRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCategory: LiveData<List<User>> = repository.allUser

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(quizUser: User) = viewModelScope.launch {
        repository.insert(quizUser)
    }

    fun delete(quizUser: User) = viewModelScope.launch {
        repository.delete(quizUser)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    suspend fun userExists(title: String): Boolean {
        return repository.userExists(title)
    }
}

class UserViewModelFactory(private val repository: QuizUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizUserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}