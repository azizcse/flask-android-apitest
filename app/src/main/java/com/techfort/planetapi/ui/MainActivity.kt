package com.techfort.planetapi.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.techfort.planetapi.R
import com.techfort.planetapi.ui.login.ActivityLogin
import com.techfort.planetapi.ui.register.ActivityRegistration
import com.techfort.planetapi.ui.users.ActivityUsersList
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val mainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initObserver()
        security()

        btn_register.setOnClickListener {
            startActivity(Intent(this,ActivityRegistration::class.java))
        }

        btn_login.setOnClickListener {
            startActivity(Intent(this, ActivityLogin::class.java))
        }

        btn_users.setOnClickListener {
            startActivity(Intent(this, ActivityUsersList::class.java))
        }

    }


    fun initObserver() {
        mainViewModel.getLoginResponse().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

    fun onButtonClick(view: View) {
        //val email = et_email.text.toString()
       // val password = et_password.text.toString()

        /*if(TextUtils.isEmpty(email) ||  TextUtils.isEmpty(password)){
            return
        }*/

        mainViewModel.getResult()
    }

    fun security(){
        val packages =packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val mypackage = applicationContext.packageName
        Log.d("FragmentActivity","My package :" + mypackage)
        /*for (packageInfo in packages) {
            Log.d("FragmentActivity","Installed package :" + packageInfo.packageName)
            Log.d("FragmentActivity", "Source dir : " + packageInfo.sourceDir)
            Log.d("FragmentActivity", "App name : " + packageInfo.name)
            Log.d("FragmentActivity", "Min sdk : " + packageInfo.minSdkVersion)
            Log.d("FragmentActivity", "Target sdk : " + packageInfo.targetSdkVersion)
            Log.d("FragmentActivity","Launch Activity :" + packageManager.getLaunchIntentForPackage(packageInfo.packageName))
            Log.d("FragmentActivity", "-------------------------------------------------------------------------" )
        }*/
    }
}
