package com.example.islamicquis.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quizCategory")
data class QuizCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val categoryName: String,
    val categoryImage: String,
)
