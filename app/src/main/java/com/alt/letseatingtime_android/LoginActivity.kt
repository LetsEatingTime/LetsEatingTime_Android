package com.alt.letseatingtime_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alt.letseatingtime_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}