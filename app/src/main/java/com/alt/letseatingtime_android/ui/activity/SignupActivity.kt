package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.alt.letseatingtime_android.ui.fragment.signup.SignupFragment1
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var signupFragment1: SignupFragment1
    private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ibBackButton.setOnClickListener {
            //백스택을 확인 후 0이면 로그인 화면으로 그 외라면 백스택 1개를 지운다.
            if(supportFragmentManager.backStackEntryCount == 0){

                    Intent(this, LoginActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                    this.finish()
            }
            else{
                supportFragmentManager.popBackStack()
            }
        }


        // 처음에 프레그먼트1로 이동
        signupFragment1 = SignupFragment1.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signupFragment1).commit()


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }
}