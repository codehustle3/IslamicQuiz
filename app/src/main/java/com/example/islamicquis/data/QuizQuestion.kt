package com.example.islamicquis.data

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "quizQuestion")
data class QuizQuestion(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryId:Int,
    val questionText: String,
    val answer: String,
    val options: String
)
