package com.example.islamicquis.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.islamicquis.Dao.QuizDao
import com.example.islamicquis.DbRelation.CategoryWithQuestionsAndOptions
import com.example.islamicquis.data.QuizCategory

class QuizCategoryRepository (private val historyDao: QuizDao){
    val allCategory: LiveData<List<QuizCategory>> = historyDao.getAllCategoryItem()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(category: QuizCategory) {
        historyDao.addCategory(category)
    }
    @WorkerThread
    suspend fun delete(category: QuizCategory) {
        historyDao.deleteCategory(category)
    }

    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAllCategory()
    }
    @WorkerThread
    suspend fun categoryExists(title: String): Boolean {
        return historyDao.categoryExists(title)
    }
//    suspend fun getCategoryWithQuestionsAndOptions(categoryId: Int): CategoryWithQuestionsAndOptions? {
//        return historyDao.getCategoryWithQuestionsAndOptions(categoryId)
//    }
//    suspend fun getRandomQuestionsByCategoryLiveData(categoryId: Int): LiveData<List<CategoryWithQuestionsAndOptions>> {
//        return historyDao.getRandomQuestionsByCategoryLiveData(categoryId)
//    }
}