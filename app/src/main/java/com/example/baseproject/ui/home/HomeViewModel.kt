package com.example.baseproject.ui.home

import android.util.Log
import com.example.baseproject.di.IoDispatcher
import androidx.lifecycle.viewModelScope
import com.example.baseproject.ui.base.BaseViewModel
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.repository.MailRepository
import com.example.baseproject.data.repository.PrefsRepository
import com.example.baseproject.ui.detail.DetailFragment
import com.example.baseproject.ui.detail.DetailFragment.Action.*
import com.example.baseproject.usecase.ListMailUserCase
import com.example.baseproject.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mailRepository: MailRepository,
    private val prefsRepository: PrefsRepository,
    private val listMailUserCase: ListMailUserCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel() {
    val accountEmail: String by lazy {
        prefsRepository.getCurrentAccountEmail()
    }
    var folderId: String? = null //will be passed from activityViewModel

    private val _mailUiState = MutableStateFlow(MailUiState())
    val mailUiState: StateFlow<MailUiState> = _mailUiState.asStateFlow()
    private val _toastUiState = MutableStateFlow(ToastUiState())
    val toastUiState: StateFlow<ToastUiState> = _toastUiState.asStateFlow()
    private val _refreshUiState = MutableStateFlow(RefreshUiState())
    val refreshUiState: StateFlow<RefreshUiState> = _refreshUiState.asStateFlow()

    fun refreshMails() {
        val myJob = viewModelScope.launch(refreshMailsExceptionHandler) {
            _refreshUiState.update { it.copy(isRefreshing = true) }
            when (val result = listMailUserCase.getMailsFromServerWithRefreshToken(accountEmail, folderId)) {
                is Result.Error -> {
                    _toastUiState.update { it.copy(message = result.exception.message) }
                }
                is Result.Success -> {
                    _toastUiState.update { it.copy(message = "Refreshed mails") }
                    Log.d("HomeViewModel", "refreshMails get mails success: update _mailUiState")
                    _mailUiState.update { it.copy(mails = result.data, scrollToTop = true) }
                    mailRepository.deleteOldAndSaveNewMailsToDB(accountEmail, folderId, result.data)
                }
            }
            _refreshUiState.update { it.copy(isRefreshing = false) }
        }
        listOfJobs.add(myJob)
    }

    fun getMailsInitData() {
        Log.d("HomeViewModel", "getMailsInitData: ")
        val myJob = viewModelScope.launch(getMailsExceptionHandler) {
            Log.d("HomeViewModel", "getMailsInitData: update _mailUiState loading")
            _mailUiState.update { it.copy(isLoading = true) }
            when (val result = listMailUserCase.getMailsInitDataAndSaveToDB(accountEmail, folderId)) {
                is Result.Error -> {
                    Log.d("HomeViewModel", "getMailsInitData: update _mailUiState error: ${result.exception.message}")
                    _mailUiState.update { it.copy(isLoading = false) }
                    _toastUiState.update { it.copy(message = result.exception.message) }
                }
                is Result.Success -> {
                    Log.d("HomeViewModel", "getMailsInitData: update _mailUiState success")
                    _mailUiState.update {
                        it.copy(
                            isLoading = false,
                            mails = result.data,
                        )
                    }
                    _toastUiState.update { it.copy(message = "Success getting mail from server") }
                }
            }
        }
        listOfJobs.add(myJob)
    }

    fun onToastDismissed() {
        Log.d("HomeViewModel", "onToastDismissed: ")
        _toastUiState.update {
            it.copy(
                message = null
            )
        }
    }

    fun updateDataOnBackFromDetail(emailForDetailView: EmailObject?, detailAction: DetailFragment.Action?) {
        if(detailAction == null)
            return
        val listMails = when(detailAction){
            UPDATE -> {
                Utils.updateList(_mailUiState.value.mails, emailForDetailView)
            }
            DELETE -> Utils.removeFromList(_mailUiState.value.mails, emailForDetailView)
        }
        Log.d("HomeViewModel", "updateDataOnBackFromDetail: update _mailUiState")
        _mailUiState.update { it.copy(mails = listMails) }
    }

    fun onRcvScrolledToTop() {
        Log.d("HomeViewModel", "onRcvScrolledToTop: update _mailUiState")
        _mailUiState.update { it.copy(scrollToTop = false) }
    }

    fun deleteData() {
        //todo
    }

    private val getMailsExceptionHandler = CoroutineExceptionHandler { _, e ->
        Log.d("HomeViewModel", "update _mailUiState CoroutineExceptionHandler: ${e.message}")
        _mailUiState.update {
            it.copy(isLoading = false)
        }
        _toastUiState.update { it.copy(message = e.message) }
    }

    private val refreshMailsExceptionHandler = CoroutineExceptionHandler { _, e ->
        _refreshUiState.update { it.copy(isRefreshing = false) }
        _toastUiState.update { it.copy(message = e.message) }
    }
}

data class MailUiState(
    val isLoading: Boolean = false,
    val mails: List<EmailObject> = listOf(),
    val scrollToTop: Boolean = false
)

data class ToastUiState(
    val message: String? = null
)

data class RefreshUiState(
    val isRefreshing: Boolean = false
)