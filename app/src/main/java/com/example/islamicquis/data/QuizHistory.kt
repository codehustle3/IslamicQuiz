package com.example.islamicquis.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "QuizHistory")
data class QuizHistory (
    @PrimaryKey(autoGenerate = true)
    var uidValue: Int,
    val userId:Int,
    var quizScourValue: String,
    var quizTotalScore: String,
    var quizCategoryValue: String,
    var categoryImage: String,
    var timeTaken: String,
)
