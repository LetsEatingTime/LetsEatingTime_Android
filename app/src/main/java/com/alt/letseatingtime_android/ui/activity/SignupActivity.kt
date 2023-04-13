package com.alt.letseatingtime_android.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.alt.letseatingtime_android.R
import com.alt.letseatingtime_android.databinding.ActivitySignupBinding
import com.alt.letseatingtime_android.ui.fragment.SignupFragment1

class SignupActivity : AppCompatActivity() {
    private lateinit var signupFragment1: SignupFragment1
    private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 처음에 프레그먼트1로 이동
        signupFragment1 = SignupFragment1.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signupFragment1).commit()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}