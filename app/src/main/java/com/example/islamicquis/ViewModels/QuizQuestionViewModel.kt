package com.example.islamicquis.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.islamicquis.Repository.QuizRepository
import com.example.islamicquis.data.QuizQuestion
import kotlinx.coroutines.launch

class QuizQuestionViewModel(private val repository: QuizRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allQuestion: LiveData<List<QuizQuestion>> = repository.allQuestion
    private val _questionLiveData = MutableLiveData<QuizQuestion?>()
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     **/
    fun insert(question: QuizQuestion) = viewModelScope.launch {
        repository.insert(question)
    }
    fun update(question: QuizQuestion) = viewModelScope.launch {
        repository.update(question)
    }

    fun delete(question: QuizQuestion) = viewModelScope.launch {
        repository.delete(question)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }
    fun getRandomQuestionsByCategory(categoryId:Int): LiveData<List<QuizQuestion>> {
        return repository.getRandomQuestionsByCategory(categoryId)
    }
    suspend fun questionExists(title: String, answers:String): Boolean {
        return repository.questionExists(title, answers)
    }
    suspend fun getQuestionById(questionId: Int): QuizQuestion? {
        return repository.getQuestionById(questionId)
    }
}

class QuestionViewModelFactory(private val repository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizQuestionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizQuestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}