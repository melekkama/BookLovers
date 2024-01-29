package com.example.cherubook.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cherubook.models.api.Error
import com.example.cherubook.models.api.PaginationModel
import com.example.cherubook.models.books.Book
import com.example.cherubook.models.userBookRates.UserBookRate
import com.example.cherubook.services.apiServices.BooksService
import com.example.cherubook.services.apiServices.UserBookRateService
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() , IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()
    var books:MutableLiveData<PaginationModel<Book>> = MutableLiveData()
    fun fetchBooks(search:String?="",pageNumber:Number=1, pageSize:Number=5) {
        viewModelScope.launch {
            var response= BooksService.searchBooks(pageNumber,pageSize,search)
            if (!response.isSuccessful)
                errorState.value= response.error
            else
                books.value= response.data!!
        }
    }
}