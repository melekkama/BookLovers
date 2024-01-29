package com.example.cherubook.ui.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cherubook.R
import com.example.cherubook.adapters.BooksAdapter
import com.example.cherubook.adapters.UserRatesAdapter
import com.example.cherubook.databinding.FragmentHomeBinding
import com.example.cherubook.databinding.FragmentSearchBinding
import com.example.cherubook.ui.home.HomeFragmentDirections
import com.example.cherubook.ui.home.HomeViewModel
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.MainApplication

class SearchFragment : Fragment(R.layout.fragment_search)   {

    private lateinit var viewModel: SearchViewModel
    private var fragmentSearchBinding: FragmentSearchBinding? = null



    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var booksAdapter: BooksAdapter

    var isLoading = false
    var isLastPage = false
    var currentPage = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        val binding= FragmentSearchBinding.bind(view)
        fragmentSearchBinding=binding
        MainActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        MainActivity.setErrorStatus(viewModel, viewLifecycleOwner)
        var query= arguments?.getString(queryKey)

        viewModel.fetchBooks(search = query,pageNumber = currentPage)


        linearLayoutManager = LinearLayoutManager(MainApplication.applicationContext())

        binding.searchRvBooks.layoutManager = linearLayoutManager


        viewModel.books.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            if (it.pageNumber == 1) {
                binding.searchRvBooks.apply {
                    booksAdapter = BooksAdapter(it.data) { book ->
                        val action = SearchFragmentDirections.actionSearchFragmentNavToBookDetailFragment(book)
                        Navigation.findNavController(view).navigate(action)
                    }
                    adapter = booksAdapter
                }
                return@observe
            }

            if (it.hasNextPage) {
                booksAdapter.removeLoading()
                isLoading = false
                isLastPage = true
                return@observe
            }


            booksAdapter.removeLoading()
            isLoading = false
            booksAdapter.addBooks(it.data)
        }

        binding.searchRvBooks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
                        booksAdapter.addLoading()
                        currentPage ++
                        viewModel.fetchBooks(query,currentPage)
                    }
                }
            }
        })

    }
    override fun onResume() {
        currentPage = 1
        isLoading = false
        isLastPage = false
        var query= arguments?.getString(queryKey)
        viewModel.fetchBooks(query,currentPage)
        super.onResume()
    }
    override fun onDestroy() {
        fragmentSearchBinding = null
        super.onDestroy()
    }

    companion object {
        private const val queryKey = "query"
    }
}