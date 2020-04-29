package com.techfort.planetapi.ui.register

import android.app.ProgressDialog
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techfort.planetapi.R
import com.techfort.planetapi.util.PrefKey
import com.techfort.planetapi.util.PrefUtil
import com.techfort.planetapi.util.Status
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class ActivityRegistration : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(RegisterViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        btn_submit.setOnClickListener {
            val emai = et_email.text.toString().trim()
            val firstName = et_first_name.text.toString().trim()
            val lastName = et_last_name.text.toString().trim()
            val password = et_password.text.toString().trim()
            if (!TextUtils.isEmpty(emai)
                || !TextUtils.isEmpty(firstName)
                || !TextUtils.isEmpty(lastName)
                || !TextUtils.isEmpty(password)
            ) {
                viewModel.register(emai, firstName, lastName, password)
            }
        }
        initViewModel()
    }

    fun initViewModel(){
        viewModel.registerLiveData.observe(this, Observer {
            it?.let {apiRes->
                when(apiRes.status){
                    Status.LOADING->{
                        toggleProgress(true)
                    }
                    Status.SUCCESS->{
                        toggleProgress(false)
                        val response = apiRes.data as Response<ResponseBody>
                        if(response.code() == 200){
                            val json = response.body()?.string()
                            val jsonstr = JSONObject(json)
                            PrefUtil.set(PrefKey.EMAIL, jsonstr.getString("email"))
                            PrefUtil.set(PrefKey.PASSWORD, jsonstr.getString("password"))
                            Toast.makeText(this,json, Toast.LENGTH_LONG).show()
                        }else{
                            Toast.makeText(this,response.message(), Toast.LENGTH_LONG).show()
                        }

                    }
                    Status.ERROR->{
                        toggleProgress(false)
                        Toast.makeText(this,"Error occurred", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    lateinit var dialog : ProgressDialog
    fun toggleProgress(isNeedToShow: Boolean){
        if(isNeedToShow){
            dialog = ProgressDialog(this)
            dialog.setMessage("Loading")
            dialog.show()
        }else{
            dialog.dismiss()
        }
    }
}