package com.example.demo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.demo.Database.CryptoDatabase
import com.example.demo.Repository.cryptoFactory
import com.example.demo.Repository.cryptoRepository
import com.example.demo.ViewModel.cryptoViewModel
import com.example.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: cryptoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }
        setUpVM()
    }

    fun setUpVM(){
        val repo= cryptoRepository(CryptoDatabase(this))
        val factory= cryptoFactory(application, repo)
        viewModel= ViewModelProvider(this, factory)[cryptoViewModel::class.java]
    }
}