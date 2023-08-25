package com.example.islamicquis.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.islamicquis.Dao.QuizDao
import com.example.islamicquis.data.QuizQuestion

class QuizRepository (private val historyDao: QuizDao){
    val allQuestion: LiveData<List<QuizQuestion>> = historyDao.getAllQuestionItem()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(quizQuestion: QuizQuestion) {
        historyDao.addQuestion(quizQuestion)
    }
    @WorkerThread
    suspend fun update(quizQuestion: QuizQuestion) {
        historyDao.addQuestion(quizQuestion)
    }
    @WorkerThread
    suspend fun delete(quizQuestion: QuizQuestion) {
        historyDao.deleteQuestion(quizQuestion)
    }

    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAllQuestion()
    }
    @WorkerThread
    suspend fun questionExists(title: String, answers:String): Boolean {
        return historyDao.questionExists(title, answers)
    }
    @WorkerThread
    fun getRandomQuestionsByCategory(categoryId:Int): LiveData<List<QuizQuestion>> {
        return historyDao.getRandomQuestionsByCategory(categoryId)
    }

    suspend fun getQuestionById(questionId: Int): QuizQuestion? {
        return historyDao.getQuestionById(questionId)
    }
}