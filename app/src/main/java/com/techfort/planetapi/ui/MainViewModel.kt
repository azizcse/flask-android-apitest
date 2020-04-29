package com.techfort.planetapi.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.techfort.taakri.data.remote.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.Response

class MainViewModel : ViewModel {
    private var loginResponse: MutableLiveData<String>

    constructor() {
        loginResponse = MutableLiveData()
    }

    fun login(email: String, password: String) {
       /* Repository.signIn(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                onSignInResponse(response)
            }, { throwAble ->
                onError(throwAble)
            })*/
    }

    fun getResult() {
        Repository.getResult()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->onSignInResponse(response)
            }, { throwAble ->onError(throwAble)
            })
    }

    private fun onSignInResponse(value: Response<ResponseBody>) {
        loginResponse.postValue(value.body()?.string())
    }

    private fun onError(throwable: Throwable) {
        loginResponse.postValue(throwable.message)
    }

    fun getLoginResponse(): MutableLiveData<String> {
        return loginResponse;
    }
}