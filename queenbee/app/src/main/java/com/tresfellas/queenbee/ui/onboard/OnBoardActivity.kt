package com.tresfellas.queenbee.ui.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tresfellas.queenbee.R

class OnBoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        initViewPage()
    }

    private fun initViewPage() {
        val viewpager = findViewById<ViewPager2>(R.id.viewPager_onBoard)

        val onboard = resources.getStringArray(R.array.onboard)

        viewpager.adapter = OnBoardPagerAdapter(supportFragmentManager, lifecycle, onboard)

        viewpager.currentItem = 0
    }

    private var lastPage = 0
    var isActivityStart = false

    override fun onStop() {
        super.onStop()

        val viewpager = findViewById<ViewPager2>(R.id.viewPager_onBoard)
        lastPage = viewpager.currentItem
    }

//    override fun onDestroy() {
//        if(!isActivityStart) {
//            Firebase.analytics.logEvent("Intro_close") {
//                param("device_type", Helpers.getDeviceType())
//                param("device_UUID", Helpers.getDeviceUUID(applicationContext))
//                param("device_language", Helpers.getDeviceLanguage())
//                param("client_version", Constants.CLIENT_VERSION)
//                param("closed_page", lastPage.toString())
//            }
//        }
//
//        super.onDestroy()
//    }
}