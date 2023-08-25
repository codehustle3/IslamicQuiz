package com.example.islamicquis.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.QuizApplication
import com.example.islamicquis.R
import com.example.islamicquis.ViewModels.HistoryViewModel
import com.example.islamicquis.ViewModels.HistoryViewModelFactory
import com.example.islamicquis.adapters.HistoryAdapter
import com.example.islamicquis.database.DatabaseHelper


class QuizHistoryFragment : Fragment(R.layout.fragment_quiz_history) {
    // TODO: Rename and change types of parameters
    lateinit var quizHistoryRecycler: RecyclerView
    lateinit var backBtn: ImageView
    lateinit var navOptions: NavOptions
    private lateinit var historyAdapter: HistoryAdapter
    private val newWordActivityRequestCode = 1
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((requireActivity().application as QuizApplication).repository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backBtn = view.findViewById(R.id.back_btn)
        quizHistoryRecycler = view.findViewById(R.id.quiz_history_recycler)
        navOptions = NavOptions.Builder().
        setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        backBtn.setOnClickListener {
            view.findNavController().popBackStack(R.id.quizHistoryFragment, true)
        }
        historyAdapter  = HistoryAdapter()
        quizHistoryRecycler.adapter = historyAdapter
        quizHistoryRecycler.layoutManager = LinearLayoutManager(requireContext())
        historyViewModel.allWords.observe(viewLifecycleOwner ) { words ->
            // Update the cached copy of the words in the adapter.
//            Log.e("TAG", "onViewCreated wordViewModel: ${words.size}" )
            words.let { historyAdapter.submitList(it) }
            historyAdapter.notifyDataSetChanged()
        }
    }
}