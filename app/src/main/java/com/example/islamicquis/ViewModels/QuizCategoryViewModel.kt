package com.example.islamicquis.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.islamicquis.DbRelation.CategoryWithQuestionsAndOptions
import com.example.islamicquis.Repository.QuizCategoryRepository
import com.example.islamicquis.data.QuizCategory
import kotlinx.coroutines.launch

class QuizCategoryViewModel(private val repository: QuizCategoryRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCategory: LiveData<List<QuizCategory>> = repository.allCategory

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(category: QuizCategory) = viewModelScope.launch {
        repository.insert(category)
    }
    fun delete(category: QuizCategory) = viewModelScope.launch {
        repository.delete(category)
    }
    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    suspend fun categoryExists(title: String): Boolean {
        return repository.categoryExists(title)
    }
//    suspend fun getCategoryWithQuestionsAndOptions(categoryId: Int): CategoryWithQuestionsAndOptions? {
//        return repository.getCategoryWithQuestionsAndOptions(categoryId)
//    }
//    suspend fun getRandomQuestionsByCategoryLiveData(categoryId: Int): LiveData<List<CategoryWithQuestionsAndOptions>> {
//        return repository.getRandomQuestionsByCategoryLiveData(categoryId)
//    }
}

class CategoryViewModelFactory(private val repository: QuizCategoryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizCategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizCategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}