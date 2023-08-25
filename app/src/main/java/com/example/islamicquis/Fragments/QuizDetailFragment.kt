package com.example.islamicquis.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.example.islamicquis.R


class QuizDetailFragment : Fragment(R.layout.fragment_quiz_detail) {
    // TODO: Rename and change types of parameters
    lateinit var categoryTitle: TextView
    lateinit var quizDetailText: TextView
    lateinit var backBtn: ImageView
    lateinit var btnStartQuiz: TextView
    lateinit var args:Bundle
    lateinit var navOptions: NavOptions
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBtn = view.findViewById(R.id.back_btn)
        quizDetailText = view.findViewById(R.id.quiz_detail_categories)
        categoryTitle = view.findViewById(R.id.category_title)
        btnStartQuiz = view.findViewById(R.id.start_quiz)
        categoryTitle.text = resources.getText(R.string.quiz_detail)
//        quizDetailText.text = requireArguments().getString("Category")
        navOptions = NavOptions.Builder().
        setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        var categoryId = requireArguments()?.getInt("Category_Id")
        args = Bundle()
//        args.putString("Category", requireArguments().getString("Category"))
        args.putInt("Category_Id",categoryId!!)
        val navController by lazy { view.findNavController() }
        btnStartQuiz.setOnClickListener {

            navigateToNextFragment()

        }
        backBtn.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }
    private fun navigateToNextFragment() {


        // Remove fragments from back stack up to the specified destination
        view?.findNavController()?.popBackStack(R.id.quizDetailFragment, true)
        view?.findNavController()?.navigate(R.id.quizAnswersFragment, args, navOptions)
    }
}