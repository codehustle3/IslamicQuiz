package com.example.islamicquis.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.Fragments.MainQuizFragment
import com.example.islamicquis.R
import com.example.islamicquis.data.QuizCategory
import com.squareup.picasso.Picasso


class QuizCategoryAdapter(context: Context?) :
    ListAdapter<QuizCategory, QuizCategoryAdapter.ViewHolder>(WORDS_COMPARATOR) {
    private var ClickListener: ItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.quiz_categories_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        Picasso.get()
            .load(current.categoryImage)
            .into(holder.categoryImage);
        holder.bind(current.categoryName)

    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        var TextViewTitle: TextView
        private lateinit var cardViewCategory: RelativeLayout
        lateinit var categoryImage:ImageView
        fun bind(textTitle: String){
            TextViewTitle.text = textTitle
        }
        override fun onClick(view: View) {
            if (ClickListener != null) {
                ClickListener!!.onItemClick(view, adapterPosition, TextViewTitle.text.toString(), )
                MainQuizFragment.categoryImageURL = getItem(adapterPosition).categoryImage
            }
        }

        init {
            TextViewTitle = itemView.findViewById(R.id.quiz_categories)
            cardViewCategory = itemView.findViewById(R.id.cardViewCategory)
            categoryImage = itemView.findViewById(R.id.category_img)
            itemView.setOnClickListener(this)
        }
    }

    companion object {
        private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<QuizCategory>() {
            override fun areItemsTheSame(oldItem: QuizCategory, newItem: QuizCategory): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: QuizCategory, newItem: QuizCategory): Boolean {
                return oldItem.categoryName == newItem.categoryName
            }
        }
    }

    fun setClickListener(itemClickListener: MainQuizFragment) {
        ClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int, title:String)
    }
}