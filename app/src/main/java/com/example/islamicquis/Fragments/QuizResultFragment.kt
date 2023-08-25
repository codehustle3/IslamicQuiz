package com.example.islamicquis.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.islamicquis.R
import com.example.islamicquis.data.LastSelectedQuestion
import com.example.islamicquis.database.DatabaseHelper
import com.example.islamicquis.data.QuizHistory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class QuizResultFragment : Fragment(R.layout.fragment_quiz_result) {
    private lateinit var totalNumber: TextView
    private lateinit var obNumber: TextView
    private lateinit var attempted: TextView
    private lateinit var notAttempted: TextView
    private lateinit var quizCategories: TextView
    lateinit var remainingTime: TextView
    lateinit var dbHistory: DatabaseHelper
    lateinit var categoryImage: ImageView
    lateinit var backBtn: ImageView
    lateinit var navOptions: NavOptions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        totalNumber = view.findViewById(R.id.totalQuestions)
        obNumber = view.findViewById(R.id.correctQuestions)
        quizCategories = view.findViewById(R.id.quiz_categories)
        attempted = view.findViewById(R.id.attempted_question)
        notAttempted = view.findViewById(R.id.did_not_attempted)
        remainingTime = view.findViewById(R.id.remaining_time)
        totalNumber.text = requireArguments().getInt("totalNumber").toString()
        obNumber.text = requireArguments().getInt("obNumber")
            .toString() + " / " + requireArguments().getInt("totalNumber").toString()
        quizCategories.text = MainQuizFragment.categoryTitle
        backBtn = view.findViewById(R.id.back_btn)
        categoryImage = view.findViewById(R.id.category_img)
        remainingTime.text = requireArguments().getString("remainingTime")
        attempted.text = requireArguments().getInt("attempted").toString()
        notAttempted.text = requireArguments().getInt("notAttempted").toString()
        navOptions = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        Picasso.get()
            .load(MainQuizFragment.categoryImageURL)
            .into(categoryImage);
        backBtn.setOnClickListener {
            findNavController().popBackStack(R.id.quizResultFragment, true)
        }
        dbHistory = DatabaseHelper.getDatabase(requireContext())
        GlobalScope.launch {
            dbHistory.historyDao().addHistory(
                QuizHistory(
                    0,
                    1,
                    requireArguments().getInt("obNumber")
                        .toString() + " / " + requireArguments().getInt("totalNumber").toString(),
                    requireArguments().getInt("totalNumber").toString(),
                    MainQuizFragment.categoryTitle,
                    MainQuizFragment.categoryImageURL,
                    requireArguments().getString("remainingTime") as String
                )
            )

        }
    }

}