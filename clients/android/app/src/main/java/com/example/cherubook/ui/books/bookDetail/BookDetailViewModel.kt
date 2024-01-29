package com.example.cherubook.ui.books.bookDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cherubook.models.api.Error
import com.example.cherubook.models.userBookCollections.UserBookCollection
import com.example.cherubook.services.apiServices.UserCollectionService
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.launch

class BookDetailViewModel  : ViewModel() , IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()

    var userCollections:MutableLiveData<Array<UserBookCollection>> = MutableLiveData()
    fun fetchUserCollections() {
        viewModelScope.launch {
            var response= UserCollectionService.getUserCollections()
            if (!response.isSuccessful)
                errorState.value= response.error
            else
                userCollections.value= response.data!!
        }
    }
}