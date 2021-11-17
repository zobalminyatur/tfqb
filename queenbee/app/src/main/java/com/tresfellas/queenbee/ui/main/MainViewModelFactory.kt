package com.tresfellas.queenbee.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tresfellas.queenbee.data.MainRepository

class MainViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                mainRepository = MainRepository(
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}