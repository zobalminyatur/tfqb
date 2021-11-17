package com.tresfellas.queenbee.ui.main

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.api.services.FirebaseMessagingService
import com.tresfellas.queenbee.databinding.ActivityMainBinding
import com.tresfellas.queenbee.ui.register.LoginViewModel
import com.tresfellas.queenbee.ui.register.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel : MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController



        println("@@@@@@${CurrentUserManager.currentUser._id}")

        FirebaseApp.initializeApp(this.baseContext)

        FirebaseMessaging.getInstance()
            .subscribeToTopic("/topics/${CurrentUserManager.currentUser._id}")

        FirebaseMessagingService().createNotificationChannel(
            this, NotificationManagerCompat.IMPORTANCE_DEFAULT, true,
            "chat"
        )

//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        this.intent.extras?.let{
            navController.navigate(R.id.navigation_chat,it)
        }
    }

    private fun handleIntentChat(intent: Intent){


    }
}