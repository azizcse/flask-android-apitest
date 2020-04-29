package com.techfort.planetapi.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techfort.planetapi.util.ApiResponse
import com.techfort.taakri.data.remote.Repository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class LoginViewModel : ViewModel() {
    val responseLiveData = MutableLiveData<ApiResponse<Response<ResponseBody>>>()
    fun login(email:String, password: String){
        viewModelScope.launch {
            Repository.login(email, password)
                .onStart {
                    responseLiveData.value = ApiResponse.loading()
                }.catch {error->
                    responseLiveData.value = ApiResponse.error(error.message?:"Error message")
                }.collect {
                    responseLiveData.value = ApiResponse.success(it)
                }
        }
    }
}