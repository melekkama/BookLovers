package com.example.cherubook.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cherubook.models.api.Error
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LoadingState

class ProfileViewModel : ViewModel() , IViewModelState {
    override var loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override var errorState: MutableLiveData<Error?> = MutableLiveData()
}