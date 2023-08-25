package com.example.islamicquis.Repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.islamicquis.Dao.QuizDao
import com.example.islamicquis.data.QuizAnswers
import com.example.islamicquis.data.QuizCategory
import com.example.islamicquis.data.QuizQuestion
import com.example.islamicquis.data.User

class QuizUserRepository (private val historyDao: QuizDao){
    val allUser: LiveData<List<User>> = historyDao.getAllUserItem()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(user: User) {
        historyDao.addUser(user)
    }

    @WorkerThread
    suspend fun delete(user: User) {
        historyDao.deleteUser(user)
    }

    @WorkerThread
    suspend fun deleteAll() {
        historyDao.deleteAllUser()
    }
    @WorkerThread
    suspend fun userExists(title: String): Boolean {
        return historyDao.userExists(title)
    }
}