package com.techfort.planetapi.ui.login

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.techfort.planetapi.R
import com.techfort.planetapi.util.PrefKey
import com.techfort.planetapi.util.PrefUtil
import com.techfort.planetapi.util.Status
import kotlinx.android.synthetic.main.activity_ligin.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response

class ActivityLogin : AppCompatActivity() {
    val viewModel by lazy { ViewModelProvider(this).get(LoginViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ligin)
        val email = PrefUtil.get(PrefKey.EMAIL, "Not found")
        val passwor = PrefUtil.get(PrefKey.PASSWORD, defaultValue = "Not found")
        et_email.setText(email)
        et_password.setText(passwor)

        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()
            viewModel.login(email, password)
        }
        initViewMode()
    }

    fun initViewMode() {
        viewModel.responseLiveData.observe(this, Observer {
            it?.let { apiResponse ->
                when (apiResponse.status) {
                    Status.LOADING -> {
                        toggleProgress(true)
                    }
                    Status.SUCCESS -> {
                        toggleProgress(false)
                        val response = apiResponse.data as Response<ResponseBody>
                        if (response.code() == 200) {
                            val json = JSONObject(response.body()?.string())
                            PrefUtil.set(PrefKey.ACCSEE_TOKEN, json.getString("access_token"))
                            Toast.makeText(this, "Login success", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Bad email or password", Toast.LENGTH_LONG).show()
                        }
                    }
                    Status.ERROR -> {
                        toggleProgress(false)
                        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }

    lateinit var dialog: ProgressDialog
    fun toggleProgress(isNeedToShow: Boolean) {
        if (isNeedToShow) {
            dialog = ProgressDialog(this)
            dialog.setMessage("Loading")
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }

}