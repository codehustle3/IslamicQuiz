package com.example.islamicquiz.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.Fragments.QuizAnswersFragment
import com.example.islamicquis.R
import com.example.islamicquis.data.LastSelectedQuestion
import com.example.islamicquis.data.OptionData
import com.example.islamicquis.data.QuizAnswers


class QuizAnswerAdapter(
    private val quizAnswersFragment: QuizAnswersFragment,
    context: Context?,
    val questions: ArrayList<OptionData>,
    val questionAnswers: String
) :
    RecyclerView.Adapter<QuizAnswerAdapter.ViewHolder>() {
    var LocalContext: Context? = null
    private var Inflater: LayoutInflater
    private var rowIndex = -1
    private var selectAns: Char = 'A'

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var quizNumberQuestions: TextView
        var numberAns: TextView
        var mainLayout: RelativeLayout
        lateinit var viewLayout:View


        init {
            mainLayout = itemView.findViewById(R.id.cardViewCategory)
            quizNumberQuestions = itemView.findViewById(R.id.ans_options)
            numberAns = itemView.findViewById(R.id.select_quiz_ans)
            viewLayout = itemView.findViewById(R.id.view_layout)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = Inflater.inflate(R.layout.quiz_ans_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.mainLayout.setOnClickListener {
            rowIndex = position
            QuizNumAdapter.ansChecked = true
            selectAns = 'A'
            if (questions[rowIndex].title == questions[rowIndex].answers) {
                QuizAnswersFragment.finalScour += 1
            }
            quizAnswersFragment.quizAnsNumUpdate()
            QuizAnswersFragment.lastSelectedOptions[QuizAnswersFragment.quizNumIndex].lastSelected = position
            QuizAnswersFragment.lastSelectedOptions[QuizAnswersFragment.quizNumIndex].attemptedQuestion = "Attempted"
            Log.e("TAG", "onBindViewHolder:${questions[rowIndex].title} == ${questions[rowIndex].answers} ")
            notifyDataSetChanged()
        }

        if (rowIndex == -1) {
            if(QuizAnswersFragment.lastSelectedOptions[QuizAnswersFragment.quizNumIndex].id == questions[position].id){
                rowIndex = QuizAnswersFragment.lastSelectedOptions[QuizAnswersFragment.quizNumIndex].lastSelected
            }
        }
        Log.e("TAG", "onBindViewHolder: ")
        QuizNumAdapter.ansChecked = rowIndex != -1
        if (rowIndex == position) {
            holder.numberAns.background =
                LocalContext!!.getDrawable(R.drawable.selected_ans)
            holder.quizNumberQuestions.setTextColor(Color.parseColor("#2F95E8"));
            holder.viewLayout.setBackgroundColor(Color.parseColor("#2F95E8"))
//            current. = RowIndex
        } else {
            holder.numberAns.background =
                LocalContext!!.getDrawable(R.drawable.bg_round_next)
            holder.quizNumberQuestions.setTextColor(Color.parseColor("#999999"));
            holder.viewLayout.setBackgroundColor(Color.parseColor("#999999"))
//            LessonData[position].lastSelected = -1
        }
        holder.quizNumberQuestions.text = questions[position].title
        holder.numberAns.text = selectAns++.toString()
    }


    init {
        Inflater = LayoutInflater.from(context)
        LocalContext = context
//        ShowDetails = ShowDetailsParam
    }
}