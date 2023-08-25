package com.example.islamicquis.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.islamicquis.Repository.QuizHistoryRepository
import com.example.islamicquis.data.QuizAnswers
import com.example.islamicquis.data.QuizHistory
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: QuizHistoryRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allWords: LiveData<List<QuizHistory>> = repository.allWords

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: QuizHistory) = viewModelScope.launch {
        repository.insert(word)
    }

    fun delete(history: QuizHistory) = viewModelScope.launch {
        repository.delete(history)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    suspend fun historyExists(title: String): Boolean {
        return repository.historyExists(title)
    }
}

class HistoryViewModelFactory(private val repository: QuizHistoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
