package com.example.demo.Repository

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo.ViewModel.cryptoViewModel

class cryptoFactory(
    val application: Application,
    val repo: cryptoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return cryptoViewModel(application, repo) as T
    }
}