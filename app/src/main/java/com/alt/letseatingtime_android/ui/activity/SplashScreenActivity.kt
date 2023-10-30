package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime.databinding.ActivitySplashScreenBinding
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val splashTimeOut: Long = 1500

    private val viewModel by lazy {
        ViewModelProvider(this)[UserActivityViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if(prefs.autoLogin){
                viewModel.getProfile()
            } else{
                logout()
            }

            viewModel.userData.observe(this){
                login()
            }

            viewModel.toastMessage.observe(this){
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
            viewModel.logout.observe(this){
                logout()
            }
        }, splashTimeOut)
    }

    private fun login(){
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun logout(){
        prefs.remove()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

}