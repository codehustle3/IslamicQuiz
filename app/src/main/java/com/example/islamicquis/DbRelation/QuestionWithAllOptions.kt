package com.example.islamicquis.DbRelation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.islamicquis.data.QuizAnswers
import com.example.islamicquis.data.QuizQuestion

data class QuestionWithAllOptions(
    @Embedded val question: QuizQuestion,
    @Relation(
        parentColumn = "id",
        entityColumn = "questionId"
    )
    val options: List<QuizAnswers>
)

