package com.techfort.planetapi.ui.register

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

class RegisterViewModel : ViewModel() {
    val registerLiveData = MutableLiveData<ApiResponse<Response<ResponseBody>>>()

    fun register(email:String, firstName:String,lastName:String, password: String){
        viewModelScope.launch {
           Repository.register(email, firstName, lastName, password)
               .onStart {
                   registerLiveData.value = ApiResponse.loading()
               }.catch {error->
                   registerLiveData.value = ApiResponse.error(error.message?:"Exception")
               }.collect {
                   registerLiveData.value = ApiResponse.success(it)
               }
        }
    }
}