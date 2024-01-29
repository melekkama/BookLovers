package com.example.cherubook.utility

import androidx.lifecycle.MutableLiveData
import com.example.cherubook.models.api.Error

interface IViewModelState {
    var loadingState: MutableLiveData<LoadingState>
    var errorState:MutableLiveData<Error?>
}