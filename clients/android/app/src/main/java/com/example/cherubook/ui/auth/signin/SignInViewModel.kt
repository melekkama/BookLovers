package com.example.cherubook.ui.auth.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cherubook.models.api.Error
import com.example.cherubook.models.users.requests.UserSignInRequest
import com.example.cherubook.services.apiServices.AuthService
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.launch


class SignInViewModel : ViewModel() , IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()

    fun signIn(signIn: UserSignInRequest): LiveData<Boolean> {
        loadingState.value = LoadingState.Loading
        val status = MutableLiveData<Boolean>()
        viewModelScope.launch {
            val apiResponse = AuthService.signIn(signIn)
            status.value=apiResponse.isSuccessful
            if (!apiResponse.isSuccessful)
                errorState.value=apiResponse.error
            loadingState.value=LoadingState.Loaded
        }
        return status
    }
}