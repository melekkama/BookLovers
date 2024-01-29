package com.example.cherubook.ui.home

import android.content.res.Resources
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.cherubook.R
import com.example.cherubook.adapters.BookCarouselAdapter
import com.example.cherubook.adapters.UserRatesAdapter
import com.example.cherubook.databinding.FragmentHomeBinding
import com.example.cherubook.models.books.Book
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.MainApplication
import kotlin.math.abs

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel


    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var userRatesAdapter: UserRatesAdapter

    var isLoading = false
    var isLastPage = false
    var currentPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        binding = FragmentHomeBinding.bind(view)

        MainActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        MainActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        viewModel.fetchBooks()

        linearLayoutManager = LinearLayoutManager(MainApplication.applicationContext())

        binding.rvUserBooksRates.layoutManager = linearLayoutManager

        viewModel.books.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            loadCarousel(it)
        }

        viewModel.userBookRates.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            if (it.hasNextPage) {
                userRatesAdapter.removeLoading()
                isLoading = false
                isLastPage = true
                return@observe
            }

            if (it.pageNumber == 1) {
                binding.rvUserBooksRates.apply {
                    userRatesAdapter = UserRatesAdapter(it.data,viewLifecycleOwner) { _ ->
//                        val action =
//                            HomeFragmentDirections.actionHomeFragmentNavToBookDetailFragment(
//                                userRate
//                            )
//                       Navigation.findNavController(view).navigate(action)
                    }
                    adapter = userRatesAdapter
                }
                return@observe
            }
                userRatesAdapter.removeLoading()
                isLoading = false
                userRatesAdapter.addUserRates(it.data)
        }


        binding.rvUserBooksRates.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    Log.i(
                        "okhttp",
                        "$visibleItemCount + $firstVisibleItemPosition >= $totalItemCount"
                    )
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount) {
                        isLoading = true
                        userRatesAdapter.addLoading()
                        currentPage++
                        viewModel.fetchUserBookRates(currentPage)
                    }
                }
            }
        })

    }


    override fun onResume() {
        currentPage = 1
        isLoading = false
        isLastPage = false
        viewModel.fetchUserBookRates(currentPage)
        super.onResume()
    }
    private fun loadCarousel(books:Array<Book>){
        val bookCarouselAdapter = BookCarouselAdapter(books){
            val action = HomeFragmentDirections.actionHomeFragmentNavToBookDetailFragment(it)
            Navigation.findNavController(requireView()).navigate(action)
        }
        binding.booksCarousel.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER

            val horizontalPadding = (105 * Resources.getSystem().displayMetrics.density).toInt()
            setPadding(horizontalPadding, 10, horizontalPadding, 10)
        }

        // Set adapter
        binding.booksCarousel.adapter = bookCarouselAdapter

        // Set page transformer
        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer((40 * Resources.getSystem().displayMetrics.density).toInt()))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = (0.80f + r * 0.20f)
        }
        binding.booksCarousel.setPageTransformer(compositePageTransformer)
    }

}