package com.example.islamicquis.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.Fragments.MainQuizFragment
import com.example.islamicquis.R
import com.example.islamicquis.data.QuizHistory
import com.squareup.picasso.Picasso


class HistoryAdapter : ListAdapter<QuizHistory, HistoryAdapter.WordViewHolder>(WORDS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.timeTaken, current.quizScourValue, current.categoryImage, current.quizTotalScore, current.quizCategoryValue)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val totalQuestion: TextView = itemView.findViewById(R.id.num_quiz)
        private val ObNumber: TextView = itemView.findViewById(R.id.contain_num_quiz)
        private val TimeTaken: TextView = itemView.findViewById(R.id.timeTaken)
        private var categoryImage: ImageView = itemView.findViewById(R.id.category_img)
        private var quizCategories:TextView = itemView.findViewById(R.id.quiz_categories)


        fun bind(timeTaken: String?, obNumber: String?, quizCategoryImage: String?, QuizTotalQuestion: String?, categoryTitle:String) {
            TimeTaken.text = timeTaken
            ObNumber.text = obNumber
            totalQuestion.text = QuizTotalQuestion
            quizCategories.text = categoryTitle
            Picasso.get()
                .load(quizCategoryImage)
                .into(categoryImage);
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.quiz_history_item, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<QuizHistory>() {
            override fun areItemsTheSame(oldItem: QuizHistory, newItem: QuizHistory): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: QuizHistory, newItem: QuizHistory): Boolean {
                return oldItem.quizScourValue == newItem.quizScourValue
            }
        }
    }
}