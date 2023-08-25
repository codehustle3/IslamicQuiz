package com.example.islamicquiz.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.Fragments.QuizAnswersFragment
import com.example.islamicquis.R
import com.example.islamicquis.data.LastSelectedQuestion
import com.example.islamicquis.data.QuizQuestion

class QuizNumAdapter(private val quizAnswersFragment: QuizAnswersFragment, context: Context?) :
    ListAdapter<QuizQuestion, QuizNumAdapter.ViewHolder>(WORDS_COMPARATOR) {
    private var ClickListener: ItemClickListener? = null
    var LocalContext: Context? = null
    private lateinit var Inflater: LayoutInflater
    private var focusedPosition = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_ans_num_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)

        holder.quizNumberQuestions.setOnClickListener {
            RowIndex = position
            QuizAnswersFragment.loadingFrist = true
            quizAnswersFragment.quizAnsNumPosition(
                position,
                current.id,
                current.questionText,
                current.answer
            )
            notifyDataSetChanged()
        }

        if (QuizAnswersFragment.lastSelectedOptions.size == position ) {

            QuizAnswersFragment.lastSelectedOptions.add(
                LastSelectedQuestion(
                    current.id,
                    -1,
                    "Not Attempted"
                )
            )
        }

        Log.e("TAG", "lastSelectedOptions: ${QuizAnswersFragment.lastSelectedOptions[position].lastSelected} || "+  QuizAnswersFragment.lastSelectedOptions[position].id )
        val toUpdateData = getItem(RowIndex)
        quizAnswersFragment.getQuestionsData(
            toUpdateData.id,
            toUpdateData.questionText,
            toUpdateData.answer
        )
        Log.e(
            "TAG",
            "quizAnsNumUpdate: " + toUpdateData.id + "::::: " + toUpdateData.questionText
        )
        if(QuizAnswersFragment.nextQuestionClicked){
            quizAnswersFragment.quizAnsNumPosition(RowIndex , toUpdateData.id,
                toUpdateData.questionText,
                toUpdateData.answer)

            QuizAnswersFragment.nextQuestionClicked= false
//            Log.e("TAG", "quizAnsNumUpdate: $nextQuestionId?? $nextQuestion, $nextAnswer", )
        }
        if (position == 0) {
            quizAnswersFragment.quizAnsNumPosition(RowIndex , toUpdateData.id,
                toUpdateData.questionText,
                toUpdateData.answer)
        }
        QuizAnswersFragment.quizQuestionText = current.questionText
        if (RowIndex == position) {
            holder.quizNumberQuestions.background =
                LocalContext!!.getDrawable(R.drawable.selected_ans)
        } else {
            holder.quizNumberQuestions.background =
                LocalContext!!.getDrawable(R.drawable.bg_round_next)
        }
        holder.quizNumberQuestions.text = (position + 1).toString()

    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var quizNumberQuestions: TextView

        override fun onClick(view: View) {
            if (ClickListener != null) {
                ClickListener!!.onItemClick(view, adapterPosition)
            }
        }

        init {
            quizNumberQuestions = itemView.findViewById(R.id.corount_quiz)
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        var RowIndex: Int = 0
        var ansChecked: Boolean = false
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<QuizQuestion>() {
            override fun areItemsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: QuizQuestion, newItem: QuizQuestion): Boolean {
                return oldItem.questionText == newItem.questionText
            }
        }
    }

    init {
        Inflater = LayoutInflater.from(context)
        LocalContext = context
//        ShowDetails = ShowDetailsParam
    }

    fun setFocusOnItem(position: Int) {
        // Set the focus on the item at the specified position
        focusedPosition = position
        notifyDataSetChanged()
    }

//    fun setClickListener(itemClickListener: QuizAnswersFragment) {
//        ClickListener = itemClickListener
//    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }
}
