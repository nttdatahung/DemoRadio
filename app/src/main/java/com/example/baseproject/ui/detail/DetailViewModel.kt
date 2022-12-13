package com.example.baseproject.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.data.local.entities.AccountEntity
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.MailRepository
import com.example.baseproject.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val mailRepository: MailRepository
) : BaseViewModel() {
    val updateUserState = MutableLiveData<Boolean>()

    fun updateUser(user: AccountEntity?) = viewModelScope.launch(coroutineExceptionHandler) {
        updateUserState.postValue(true)
        updateUserState.postValue(false)
    }

    fun getEmailBody(accountEmail: String, emailObject: EmailObject) {
        viewModelScope.launch(coroutineExceptionHandler) {
            when(val result = mailRepository.getBody(accountEmail, emailObject)){
                is Result.Error -> {
                    //hide loading
                }
                is Result.Success -> {
                    //hide loading, show body
                }
            }
        }
    }

}