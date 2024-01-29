package com.example.cherubook.ui.auth.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cherubook.models.api.Error
import com.example.cherubook.models.users.requests.UserSignUpRequest
import com.example.cherubook.services.apiServices.AuthService
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel(), IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()

    fun signUp(userSignUp: UserSignUpRequest): LiveData<Boolean> {
        var status = MutableLiveData<Boolean>()
        loadingState.value = LoadingState.Loading

        viewModelScope.launch {
            var res = AuthService.signUp(userSignUp)
            status.value=res.isSuccessful
            loadingState.value=LoadingState.Loaded
            if (!res.isSuccessful) errorState.value=res.error
        }
        return status
    }
}