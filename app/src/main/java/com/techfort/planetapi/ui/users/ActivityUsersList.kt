package com.techfort.planetapi.ui.users

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.techfort.planetapi.R
import com.techfort.planetapi.remot.model.UserResponse
import com.techfort.planetapi.remot.model.UserResponseItem
import com.techfort.planetapi.util.Status
import kotlinx.android.synthetic.main.activity_users.*
import okhttp3.ResponseBody
import retrofit2.Response


class ActivityUsersList : AppCompatActivity(), UserAdapter.Listener {
    val viewModel by lazy { ViewModelProvider(this).get(UserViewModel::class.java) }
    lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        initRecyclerView()

        viewModel.getUsers().observe(this, Observer {
            it?.let { apiRes ->
                when (apiRes.status) {
                    Status.LOADING -> {
                        Log.e("Loading_msg", "Loading")
                    }
                    Status.ERROR -> {
                        Log.e("Loading_msg", "Error")
                    }
                    Status.SUCCESS -> {
                        val response = apiRes.data as Response<UserResponse>
                        if(response.code() == 200) {
                            Log.e("Loading_msg", "Item : ${response.body()?.size}")
                            userAdapter.addItem(response.body()!!)
                        }else{
                            Toast.makeText(this, response.message()?:"Error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
        viewModel.deleteViewMode.observe(this, Observer {
            it?.let { apiRes ->
                when (apiRes.status) {
                    Status.LOADING -> {
                        Log.e("Loading_msg", "Loading")
                    }
                    Status.ERROR -> {
                        Log.e("Loading_msg", apiRes.message)
                    }
                    Status.SUCCESS -> {
                        val response = apiRes.data as Response<ResponseBody>
                        if(response.code() == 200) {

                            Toast.makeText(this, response.body()?.toString()?:"Success", Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this, response.message()?:"Error", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        })
    }

    fun initRecyclerView() {
        userAdapter = UserAdapter(this, this)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = userAdapter
    }

    override fun onClick(view: View, item: UserResponseItem) {
        when(view.id){
            R.id.tv_name->viewModel.deleteItem(item.id)
            R.id.btn_delete->viewModel.deleteItem(item.id)
        }
    }
}