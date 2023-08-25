package com.example.islamicquis

import android.app.Application
import com.example.islamicquis.Repository.QuizCategoryRepository
import com.example.islamicquis.Repository.QuizHistoryRepository
import com.example.islamicquis.Repository.QuizRepository
import com.example.islamicquis.Repository.QuizUserRepository
import com.example.islamicquis.database.DatabaseHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class QuizApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    private val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { DatabaseHelper.getDatabase(this) }
    val repository by lazy { QuizHistoryRepository(database.historyDao()) }
    val categoryRepository by lazy { QuizCategoryRepository(database.historyDao()) }
    val quizRepository by lazy { QuizRepository(database.historyDao()) }
    val userRepository by lazy { QuizUserRepository(database.historyDao()) }
//    val answersRepository by lazy { QuizAnswersRepository(database.historyDao()) }
}
