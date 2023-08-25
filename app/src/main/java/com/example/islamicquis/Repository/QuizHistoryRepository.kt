package com.example.islamicquis.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.islamicquis.data.QuizHistory
import com.example.islamicquis.Dao.QuizDao
import com.example.islamicquis.data.QuizQuestion

class QuizHistoryRepository (private val historyDao: QuizDao){
    val allWords: LiveData<List<QuizHistory>> = historyDao.getAllHistoryItem()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: QuizHistory) {
        historyDao.addHistory(word)
    }

    @WorkerThread
    suspend fun delete(history: QuizHistory) {
        historyDao.deleteHistory(history)
    }

    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAllHistory()
    }
    @WorkerThread
    suspend fun historyExists(title: String): Boolean {
        return historyDao.historyExists(title)
    }
}