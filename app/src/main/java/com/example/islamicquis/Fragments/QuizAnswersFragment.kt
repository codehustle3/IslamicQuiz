package com.example.islamicquis.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.R
import com.example.islamicquis.DataParser.QuizPlistDataParser
import com.example.islamicquis.QuizApplication
import com.example.islamicquis.ViewModels.QuestionViewModelFactory
import com.example.islamicquis.ViewModels.QuizQuestionViewModel
import com.example.islamicquis.data.LastSelectedQuestion
import com.example.islamicquis.data.OptionData
import com.example.islamicquiz.adapters.QuizAnswerAdapter
import com.example.islamicquiz.adapters.QuizNumAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.DecimalFormat
import java.text.NumberFormat


class QuizAnswersFragment : Fragment(R.layout.fragment_quiz_answers) {
    // TODO: Rename and change types of parameters
    private lateinit var quizRecyclerView: RecyclerView
    private lateinit var quizTitle: TextView
    private lateinit var imageUser: ImageView
    private lateinit var backBtn: ImageView
    private lateinit var SubmitBtn: TextView
    private lateinit var textTime: TextView
    private lateinit var timerQuiz: LinearLayout
    private lateinit var quizAnsRecycler: RecyclerView
    private var quizAnswerAdapter: QuizAnswerAdapter? = null

    //    var source: ArrayList<QuizQuestionsListData>? = null
    private var recyclerViewLayoutManager: RecyclerView.LayoutManager? = null
    private lateinit var horizontalLayout: LinearLayoutManager
    private lateinit var adapter: QuizNumAdapter
    private var quizID = 0

    private lateinit var options: ArrayList<OptionData>
    private lateinit var nextBtn: ImageView
    private lateinit var previousBtn: ImageView
    private var nextAnswer: String = ""
    private var nextQuestion: String = ""
    private var nextQuestionId: Int = -1
    var finalElapsedTime: String = ""

    companion object {
        var quizQuestionText: String = ""
        var finalScour = 0
        var loadingFrist = false
        var nextQuestionClicked = false
        var totalQuestions = 0
        lateinit var lastSelectedOptions: ArrayList<LastSelectedQuestion>
        var quizNumIndex = -1
        var quizAnsIndex = -1
    }

    //    private val answersViewModel: QuizOptionsViewModel by viewModels {
//        AnswersViewModelFactory((requireActivity().application as QuizApplication).answersRepository)
//    }
    private val questionViewModel: QuizQuestionViewModel by viewModels {
        QuestionViewModelFactory((requireActivity().application as QuizApplication).quizRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        quizRecyclerView = view.findViewById(R.id.quiz_num_Recycler)
        quizTitle = view.findViewById(R.id.quiz_title)
        imageUser = view.findViewById(R.id.user_img)
        backBtn = view.findViewById(R.id.back_btn)
        imageUser.visibility = View.GONE
        timerQuiz = view.findViewById(R.id.quiz_timer)
        textTime = view.findViewById(R.id.text_time)
        SubmitBtn = view.findViewById(R.id.submit_quiz)
        timerQuiz.visibility = View.VISIBLE
        quizAnsRecycler = view.findViewById(R.id.quiz_ans_recycler)
        nextBtn = view.findViewById(R.id.next_q)
        previousBtn = view.findViewById(R.id.previous_q)
        quizID = requireArguments().getInt("Category_Id")
        quizRecyclerView.layoutManager = recyclerViewLayoutManager;
        options = arrayListOf()
        lastSelectedOptions = arrayListOf()
        quizNumIndex = -1
        quizAnsIndex = -1
        adapter = QuizNumAdapter(
            this,
            requireContext(),
        )
        horizontalLayout = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        );
        if (quizID == 0) {
            quizID = 1
        }

        questionViewModel.getRandomQuestionsByCategory(quizID).observe(viewLifecycleOwner) { item ->
            item.let {

                adapter.submitList(item)
            }
        }
        var navOptions = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        quizRecyclerView.layoutManager = horizontalLayout;
        quizRecyclerView.adapter = adapter;
        time()
        SubmitBtn.setOnClickListener {
            openResultScreen()
        }
        quizTitle.text = quizQuestionText
        previousBtn.setOnClickListener { previousQuestion() }
    }


    private fun time() {
        val initialDuration: Long = 10 * 60 * 1000 // 10 minutes in milliseconds

        object : CountDownTimer(initialDuration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val elapsedTime = initialDuration - millisUntilFinished

                val f: NumberFormat = DecimalFormat("00")
                var hour = millisUntilFinished / 3600000 % 24
                var min = millisUntilFinished / 60000 % 60
                var sec = millisUntilFinished / 1000 % 60
                textTime.text =
                    (f.format(hour) + ":" + f.format(min)).toString() + ":" + f.format(sec)

                hour = elapsedTime / 3600000 % 24
                min = elapsedTime / 60000 % 60
                sec = elapsedTime / 1000 % 60
                finalElapsedTime = (f.format(hour) + ":" + f.format(min)).toString() + ":" + f.format(sec)

            }

            override fun onFinish() {
                textTime.text = "00:00:00"
            }
        }.start()
    }

    private fun previousQuestion() {
        if (QuizNumAdapter.RowIndex > 0) {
            nextQuestionClicked = true
            QuizNumAdapter.RowIndex = QuizNumAdapter.RowIndex - 1
            adapter!!.notifyDataSetChanged()

            nextBtn.background = requireContext().getDrawable(R.drawable.bg_round_next)
            Log.e(
                "TAG",
                "quizAnsNumUpdate: " + QuizNumAdapter.ansChecked + "::::: " + quizAnsIndex
            )
            QuizNumAdapter.ansChecked = false
            changeItemFocus(QuizNumAdapter.RowIndex)
        }
    }

    fun quizAnsNumUpdate() {
        nextBtn.background = requireContext().getDrawable(R.drawable.selected_ans)
        Log.e("TAG", "quizAnsNumUpdate: $quizAnsIndex")
        nextBtn.setOnClickListener {
            if (QuizNumAdapter.ansChecked && QuizNumAdapter.RowIndex < 9) {
                nextQuestionClicked = true
                QuizNumAdapter.RowIndex = QuizNumAdapter.RowIndex + 1
                adapter!!.notifyDataSetChanged()

                nextBtn.background = requireContext().getDrawable(R.drawable.bg_round_next)
                Log.e(
                    "TAG",
                    "quizAnsNumUpdate: " + QuizNumAdapter.ansChecked + "::::: " + quizAnsIndex
                )
                QuizNumAdapter.ansChecked = false
                changeItemFocus(QuizNumAdapter.RowIndex)
            }
        }
    }

    private fun openResultScreen() {
        var attempted: Int = 0
        var notAttempted: Int = 0
        var navOptions = NavOptions.Builder().setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        for (item in lastSelectedOptions.indices) {
            if (lastSelectedOptions[item].attemptedQuestion == "Not Attempted") {
                notAttempted += 1
            }
            if (lastSelectedOptions[item].attemptedQuestion == "Attempted") {
                attempted += 1
            }
        }

        var args = Bundle()
        args.putInt("totalNumber", 10)
        args.putInt("obNumber", finalScour)
        args.putString("Category", requireArguments().getString("Category"))
        args.putString("remainingTime", finalElapsedTime)
        args.putInt("attempted", attempted)
        args.putInt("notAttempted", notAttempted)
        findNavController().popBackStack(R.id.quizAnswersFragment, true)
        findNavController().navigate(R.id.quizResultFragment, args, navOptions)
        finalScour = 0

    }

    fun quizAnsNumPosition(ListIndex: Int, questionId: Int, questionText: String, answer: String) {
        getOptionList(questionId, questionText, answer)
        quizNumIndex = ListIndex
        quizAnsIndex = ListIndex
        updateQuizAnsList()
    }

    private fun updateQuizAnsList() {
        Log.e("TAG", "updateQuizAnsList: $quizAnsIndex")


    }

    private fun changeItemFocus(position: Int) {
        adapter?.setFocusOnItem(position)
        quizRecyclerView.scrollToPosition(position)
        quizRecyclerView.post {
            quizRecyclerView.findViewHolderForAdapterPosition(position)?.itemView?.requestFocus()
        }
    }

    fun getOptionList(questionId: Int, questionText: String, answer: String) {
        options.clear()
        options = arrayListOf()
        var questionDataText = ""
        quizTitle.text = questionText
        runBlocking {
            val question = questionViewModel.getQuestionById(questionId)
            Log.e(
                "TAG",
                "getOptionListdatadata: ${question?.options} ||| ${question?.id} ||| $questionId "
            )
            val splitArray = questionViewModel.getQuestionById(questionId)?.options
                ?.removeSurrounding("|||", ",")
                ?.split("|||")

            val arrayList = ArrayList(splitArray)
            Log.e("TAG", "getOptionLis: $arrayList ${arrayList.size} ")
            println(arrayList)
            var lastQuestion = ""
            for (item in arrayList.indices) {
                if (lastQuestion != arrayList[item]) {
                    if (arrayList[item] != "" && options.size < 4) {
                        options.add(
                            OptionData(
                                questionId,
                                arrayList[item],
                                question!!.answer,
                            )
                        )
                        lastQuestion = arrayList[item]
                        Log.e(
                            "TAG",
                            "getOptionListOptions: $options ${options.size} || ${
                                questionViewModel.getQuestionById(questionId)?.answer
                            }"
                        )
                    }
                }
            }

        }

//        answersViewModel.allAnswers.observe(viewLifecycleOwner){item->
//            item.let { quizAnswerAdapter!!.submitList(item) }
//            quizAnswerAdapter!!.notifyDataSetChanged()
//        }

        quizAnswerAdapter = QuizAnswerAdapter(
            this,
            requireContext(),
            options,
            questionDataText
        )
        val RecyclerViewGridLayoutManager =
            GridLayoutManager(requireContext(), 1)
        quizAnsRecycler.layoutManager = RecyclerViewGridLayoutManager
        quizAnsRecycler.adapter = quizAnswerAdapter


    }

    fun getQuestionsData(questionId: Int, questionText: String, answer: String) {
        nextQuestionId = questionId
        nextAnswer = answer
        nextQuestion = questionText
    }
}