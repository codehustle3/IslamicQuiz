package com.example.islamicquis.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.islamicquis.data.QuizCategory
import com.example.islamicquis.data.QuizHistory
import com.example.islamicquis.data.QuizQuestion
import com.example.islamicquis.data.User

@Dao
interface QuizDao {

    @Insert()
    suspend fun addHistory(quizHistory: QuizHistory)

    @Delete
    suspend fun deleteHistory(quizHistory: QuizHistory)
    @Query("SELECT * FROM QuizHistory")
    fun getAllHistoryItem(): LiveData<List<QuizHistory>>
    @Query("DELETE FROM QuizHistory")
    suspend fun deleteAllHistory()
    @Query("SELECT EXISTS (SELECT 1 FROM quizhistory WHERE quizCategoryValue = :title LIMIT 1)")
    suspend fun historyExists(title: String): Boolean

    @Insert()
    suspend fun addCategory(quizCategory: QuizCategory)

    @Query("SELECT EXISTS (SELECT 1 FROM quizCategory WHERE categoryName = :title LIMIT 1)")
    suspend fun categoryExists(title: String): Boolean

    @Delete
    suspend fun deleteCategory(quizCategory: QuizCategory)
    @Query("DELETE FROM quizCategory")
    suspend fun deleteAllCategory()

    @Query("SELECT * FROM quizCategory")
    fun getAllCategoryItem(): LiveData<List<QuizCategory>>

    @Insert()
    suspend fun addUser(quizUser: User)

    @Delete
    suspend fun deleteUser(quizUser: User)

    @Query("SELECT * FROM users")
    fun getAllUserItem(): LiveData<List<User>>
    @Query("DELETE FROM users")
    suspend fun deleteAllUser()
    @Query("SELECT EXISTS (SELECT 1 FROM users WHERE userEmail = :title LIMIT 1)")
    suspend fun userExists(title: String): Boolean

    @Insert()
    suspend fun addQuestion(quizQuestion: QuizQuestion)

    @Query("SELECT EXISTS (SELECT 1 FROM quizQuestion WHERE questionText = :title AND answer = :answers)")
    suspend fun questionExists(title: String, answers:String): Boolean

    @Delete
    suspend fun deleteQuestion(quizQuestion: QuizQuestion)
    @Query("DELETE FROM quizQuestion")
    suspend fun deleteAllQuestion()

    @Query("SELECT * FROM quizQuestion")
    fun getAllQuestionItem(): LiveData<List<QuizQuestion>>

    @Query("SELECT * FROM quizQuestion WHERE categoryId = :categoryId ORDER BY RANDOM() LIMIT 10")
    fun getRandomQuestionsByCategory(categoryId: Int): LiveData<List<QuizQuestion>>

    @Query("SELECT * FROM quizQuestion WHERE id = :questionId LIMIT 1")
    suspend fun getQuestionById(questionId: Int): QuizQuestion?
}