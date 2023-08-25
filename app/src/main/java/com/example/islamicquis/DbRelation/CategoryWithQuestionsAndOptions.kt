package com.example.islamicquis.DbRelation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.islamicquis.data.QuizCategory
import com.example.islamicquis.data.QuizQuestion

data class CategoryWithQuestionsAndOptions(
    @Embedded val category: QuizCategory,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val questionsWithOptions: List<QuizQuestion>
)
