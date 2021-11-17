package com.tresfellas.queenbee.ui.splash

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.api.managers.TokenManager
import com.tresfellas.queenbee.api.methods.UserApi
import com.tresfellas.queenbee.data.model.HttpErrorCode
import com.tresfellas.queenbee.ui.main.MainActivity
import com.tresfellas.queenbee.ui.onboard.OnBoardActivity
import com.tresfellas.queenbee.ui.register.LoginActivity
import com.tresfellas.queenbee.utils.RxUtil
import com.tresfellas.queenbee.utils.SharedPreference
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

//    @Inject
//    lateinit var appUpdateManager: AppUpdateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        checkAppUpdate()
        checkLogin()

//        val getStatusBarHeight = getStatusBarHeight()
//        (this.application as MyApplication).statusBarHeight = getStatusBarHeight
    }

//    private fun checkAppUpdate() {
//        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
//        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
//                // Request the update.
//                try {
//                    val installType = when {
////                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> AppUpdateType.FLEXIBLE
//                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> AppUpdateType.IMMEDIATE
//                        else -> null
//                    }
//                    appUpdateManager.startUpdateFlowForResult(
//                        appUpdateInfo,
//                        installType!!,
//                        this,
//                        APP_UPDATE_REQUEST_CODE
//                    )
//                } catch (e: IntentSender.SendIntentException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }

//    override fun onResume() {
//        super.onResume()
//        appUpdateManager
//            .appUpdateInfo
//            .addOnSuccessListener { appUpdateInfo ->
//                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                    // If an in-app update is already running, resume the update.
//                    appUpdateManager.startUpdateFlowForResult(
//                        appUpdateInfo,
//                        AppUpdateType.IMMEDIATE,
//                        this,
//                        APP_UPDATE_REQUEST_CODE
//                    )
//                }
//            }
//    }


    private fun checkLogin() {
        val sharedPreference = SharedPreference(applicationContext)

        val accessToken = sharedPreference.getValueString("access_token").toString()
        val refreshToken = sharedPreference.getValueString("refresh_token").toString()

        println("@@@@@@SPLASH@@@@$refreshToken@@@@$accessToken")

        TokenManager.accessToken = accessToken
        TokenManager.refreshToken = refreshToken

        if (accessToken == "null" ) {
            val intent = Intent(this@SplashActivity, OnBoardActivity::class.java)
            this@SplashActivity.startActivity(intent)
            this.finish()
        } else {
            getCurrentUser()
        }

    }

    private fun getCurrentUser(){
        UserApi().getCurrentUser()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@@@@@@GETCURRENTUSER=$it")
                    CurrentUserManager.currentUser = it
                    if(it.age==0) {
                        val intent = Intent(this@SplashActivity, LoginActivity::class.java).apply {
                        }
                        this@SplashActivity.startActivityForResult(intent, 100)
                    }else{
                        val intent = Intent(this@SplashActivity, MainActivity::class.java).apply {
                        }
                        this@SplashActivity.startActivityForResult(intent, 100)
                    }
                    this.finish()

                },
                {
                    println("@@@@@@$it")
                    Toast.makeText(applicationContext, "다시 로그인 해주세요", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java).apply {
                    }
                    this@SplashActivity.startActivityForResult(intent, 100)
                }
            )
    }

//    private fun getStatusBarHeight(): Int {
//        var result = 0
//        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
//        if (resourceId > 0) {
//            result = resources.getDimensionPixelSize(resourceId)
//        }
//        return result
//    }

    companion object {
        private const val APP_UPDATE_REQUEST_CODE = 1991
    }
}