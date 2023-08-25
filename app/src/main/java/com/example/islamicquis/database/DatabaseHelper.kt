package com.example.islamicquis.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.islamicquis.Dao.QuizDao
import com.example.islamicquis.data.QuizAnswers
import com.example.islamicquis.data.QuizCategory
import com.example.islamicquis.data.QuizHistory
import com.example.islamicquis.data.QuizQuestion
import com.example.islamicquis.data.User

@Database(entities = [QuizHistory::class, QuizQuestion::class, QuizCategory::class, User::class], version = 83)
abstract class DatabaseHelper : RoomDatabase() {
    abstract fun historyDao(): QuizDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseHelper? = null

        fun getDatabase(mContext: Context): DatabaseHelper {
            if (INSTANCE == null) {
                synchronized(true) {
                    INSTANCE = Room.databaseBuilder(
                        mContext.applicationContext,
                        DatabaseHelper::class.java,
                        "quizHistory"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}