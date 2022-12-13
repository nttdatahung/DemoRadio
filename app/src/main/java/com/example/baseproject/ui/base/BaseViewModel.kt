package com.example.baseproject.ui.base

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.baseproject.common.BaseException
import com.example.baseproject.common.Event
import com.example.baseproject.common.GettingDataState
import com.example.baseproject.utils.ToastUtil
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import java.util.concurrent.CancellationException

open class BaseViewModel: ViewModel(){

    val gettingDataState = MutableLiveData<GettingDataState>()


    var baseNetworkException = MutableLiveData<Event<BaseException>>()
        protected set

//    var networkException = MutableLiveData<Event<NetworkErrorException>>()
//        protected set

    var isLoading = MutableLiveData<Event<Boolean>>()
        protected set

    var onNavigateToPage = MutableLiveData<Event<Int>>()
        protected set

    var errorMessageResourceId = MutableLiveData<Event<Int>>()
        protected set

    var notifyMessageResourceId = MutableLiveData<Event<Int>>()
        protected set

    var isLoadingMore = MutableLiveData<Event<Boolean>>()
        protected set

    var job: Job? = null
        protected set

    var listOfJobs = mutableListOf<Job>()

    protected fun registerJobFinish(){
        job?.invokeOnCompletion {
            setLoadingState(false)
        }
    }

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(TAG, "coroutineExceptionHandler: ${exception.message}")
        ToastUtil.show("coroutineExceptionHandler: ${exception.message}")
    }

    protected fun showError(messageId: Int) {
        errorMessageResourceId.postValue(Event(messageId))
    }

    protected fun showNotify(messageId: Int) {
        notifyMessageResourceId.postValue(Event(messageId))
    }

    protected fun addBaseNetworkException(exception: BaseException) {
        baseNetworkException.postValue(Event(exception))
    }

//    protected fun addNetworkException(exception: NetworkErrorException) {
//        networkException.postValue(Event(exception))
//    }

    protected fun setLoadingState(isLoading: Boolean) {
        this.isLoading.postValue(Event(isLoading))
    }

    protected fun showLoadingMore(isShow: Boolean){
        isLoadingMore.postValue(Event(isShow))
    }

    protected fun navigateToPage(actionId: Int) {
        onNavigateToPage.postValue(Event(actionId))
    }

//    protected open fun parseErrorCallApi(e: Throwable) {
//        when (e) {
//            is BaseNetworkException -> {
//                baseNetworkException.postValue(Event(e))
//            }
//            is NetworkErrorException -> {
//                networkException.postValue(Event(e))
//            }
//            else -> {
//                val unknowException = BaseNetworkException()
//                unknowException.mainMessage = e.message ?: "Something went wrong"
//                baseNetworkException.postValue(Event(unknowException))
//            }
//        }
//    }

    open fun fetchData() {

    }

    fun cancelJobs() {
        listOfJobs.forEach{
            it.cancel(CancellationException("Force cancel jobs"))
        }
    }
}