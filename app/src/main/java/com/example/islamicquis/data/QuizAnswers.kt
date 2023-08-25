package com.example.islamicquis.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizAnswers")
data class QuizAnswers(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val questionId:Int,
    val title:String,
    val answers:String
)
