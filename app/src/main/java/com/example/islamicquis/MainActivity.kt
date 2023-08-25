package com.example.islamicquis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.islamicquis.ViewModels.CategoryViewModelFactory
import com.example.islamicquis.ViewModels.QuestionViewModelFactory
import com.example.islamicquis.ViewModels.QuizCategoryViewModel
import com.example.islamicquis.ViewModels.QuizQuestionViewModel
import com.example.islamicquis.data.QuizCategory
import com.example.islamicquis.data.QuizHistory
import com.example.islamicquis.data.QuizQuestion
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    var arrayList: ArrayList<QuizHistory> = arrayListOf()
    private val categoryViewModel: QuizCategoryViewModel by viewModels {
        CategoryViewModelFactory((application as QuizApplication).categoryRepository)
    }
    private val questionViewModel: QuizQuestionViewModel by viewModels {
        QuestionViewModelFactory((application as QuizApplication).quizRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)
        val categoryId = 1
        quizDataListParser()

    }

    private fun quizDataListParser() {

        var inputStream: InputStream? = null
        try {
            inputStream = this.assets.open("islamicQuizData.json")
            val jsonString = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(jsonString)
            val categoriesArray = jsonObject.getJSONArray("categories")

            for (i in 0 until categoriesArray.length()) {
                val categoryObj = categoriesArray.getJSONObject(i)
                val category = categoryObj.getString("category")
                val categoryImage = categoryObj.getString("categoryImage")
                val questionsArray = categoryObj.getJSONArray("questions")

                println("Category: $category")
                for (j in 0 until questionsArray.length()) {
                    val questionObj = questionsArray.getJSONObject(j)
                    val question = questionObj.getString("question")
                    val optionsArray = questionObj.getJSONArray("options")
                    val optionsList = mutableListOf<String>()
                    val answer = questionObj.getString("answer")
                    var titleExists = false
                    var questionOption = ""
                    lifecycleScope.launch {

                        titleExists = questionViewModel.questionExists(question, answer)
                        if (!titleExists) {
                            for (k in 0 until optionsArray.length()) {
                                optionsList.add(optionsArray.getString(k))
                                questionOption = questionOption+ "|||" +optionsArray.getString(k).toString()
                            }
                            questionViewModel.insert(QuizQuestion(0, i + 1, question, answer, questionOption))
                        }
                    }

                    println()
                }
                var categoryExists = false
                lifecycleScope.launch {

                    categoryExists = categoryViewModel.categoryExists(category)
                    Log.e("category", "quizDataListParser: $categoryExists ||||| ${
                        categoryViewModel.categoryExists(
                            category
                        )
                    }" )
                    if (!categoryExists) {
                        Log.e("category", "quizDataListParser: $categoryExists" )
                        categoryViewModel.insert(QuizCategory(0, category, categoryImage))
                    }
                }

            }
        } catch (e: Exception) {
            Log.e("TAG", "quizDataListParser: ${e.message}")
        }

    }
}