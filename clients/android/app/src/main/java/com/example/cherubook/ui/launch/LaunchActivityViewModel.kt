package com.example.cherubook.ui.launch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cherubook.models.api.Error
import com.example.cherubook.services.apiServices.TokenService
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivityViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()

    fun tokenCheck(): LiveData<Boolean> {
        loadingState.value = LoadingState.Loading
        var status = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val res= TokenService.refreshToken()
            status.value=res.isSuccessful
            loadingState.value=LoadingState.Loaded
            if (!res.isSuccessful) errorState.value=res.error
        }
        return status
    }
}