package com.example.islamicquis.Fragments

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.islamicquis.R
import com.example.islamicquis.DataParser.QuizPlistDataParser
import com.example.islamicquis.QuizApplication
import com.example.islamicquis.ViewModels.CategoryViewModelFactory
import com.example.islamicquis.ViewModels.QuizCategoryViewModel
import com.example.islamicquis.adapters.QuizCategoryAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MainQuizFragment : Fragment(R.layout.fragment_main_quiz), QuizCategoryAdapter.ItemClickListener {
    var imageView: ImageView? = null
    lateinit var tabLayout: TabLayout
    lateinit var quizCategoriesRecycler: RecyclerView
    var mainAdapter: QuizCategoryAdapter? = null
    lateinit var backBtn: ImageView
    var navController: NavController? = null
    lateinit var bottomNav : BottomNavigationView
    lateinit var userBtn:ImageView
    companion object{
        var categoryTitle:String = ""
        var categoryImageURL:String = ""
    }

    private val categoryViewModel: QuizCategoryViewModel by viewModels {
        CategoryViewModelFactory((requireActivity().application as QuizApplication).categoryRepository)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tabLayout = view.findViewById(R.id.tabLayout)
        tabLayout.addTab(tabLayout.newTab().setText("ISLAM"))
        quizCategoriesRecycler = view.findViewById(R.id.quiz_categories_recycler)
        userBtn = view.findViewById(R.id.user_img)
        backBtn = view.findViewById(R.id.back_btn)
        backBtn.visibility = View.INVISIBLE
        userBtn.setOnClickListener {
            var navOptions = NavOptions.Builder().
            setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right)
                .build()
            navController!!.navigate(R.id.loginFragment, null, navOptions)
        }
        mainAdapter = QuizCategoryAdapter(requireContext())
        mainAdapter!!.setClickListener(this)
        quizCategoriesRecycler.adapter = mainAdapter
        mainAdapter!!.notifyDataSetChanged()
        val RecyclerViewGridLayoutManager =
            GridLayoutManager(requireContext(), 1)
        categoryViewModel.allCategory.observe(viewLifecycleOwner){
            it.let { mainAdapter!!.submitList(it) }
            mainAdapter!!.notifyDataSetChanged()
        }
        bottomNav = view.findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {

                    true
                }
                R.id.message -> {
                    var navOptions = NavOptions.Builder().
                    setEnterAnim(R.anim.slide_in_right)
                        .setExitAnim(R.anim.slide_out_left)
                        .setPopEnterAnim(R.anim.slide_in_left)
                        .setPopExitAnim(R.anim.slide_out_right)
                        .build()
                    navController!!.navigate(R.id.quizHistoryFragment, null, navOptions)
                    true
                }
                else -> {
                    false
                }

            }
        }

        quizCategoriesRecycler.layoutManager = RecyclerViewGridLayoutManager
        navController = view.findNavController()
    }

    override fun onItemClick(view: View?, position: Int, title:String) {
        var navOptions = NavOptions.Builder().
            setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .build()
        var args = Bundle()
        categoryTitle = title
        args.putInt("Category_Id", position+1)
        navController!!.navigate(R.id.quizDetailFragment, args, navOptions)
    }
}