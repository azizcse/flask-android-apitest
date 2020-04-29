package com.techfort.planetapi.ui.users

import androidx.lifecycle.*
import com.techfort.planetapi.remot.model.UserResponse
import com.techfort.planetapi.util.ApiResponse
import com.techfort.taakri.data.remote.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import java.lang.Exception

class UserViewModel : ViewModel() {
    val deleteViewMode = MutableLiveData<ApiResponse<Response<ResponseBody>>>()
    fun getUsers() = liveData(Dispatchers.IO) {
        emit(ApiResponse.loading())
        try {
            emit(ApiResponse.success(data = Repository.getUserList()))
        } catch (exception: Exception) {
            emit(ApiResponse.error(message = "Exception occurred"))
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
            Repository.deleteUser(id)
                .onStart {
                    deleteViewMode.value = ApiResponse.loading()
                }
                .catch {error->
                    deleteViewMode.value = ApiResponse.error(message = error.message?:"Exception")
                }
                .collect {
                   deleteViewMode.value = ApiResponse.success(it)
                }
        }
    }
}