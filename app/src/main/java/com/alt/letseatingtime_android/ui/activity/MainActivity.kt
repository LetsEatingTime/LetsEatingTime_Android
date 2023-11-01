package com.alt.letseatingtime_android.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ActivityMainBinding
import com.alt.letseatingtime_android.util.OnSingleClickListener

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val loginIntent = Intent(this, LoginActivity::class.java)
        val signupIntent = Intent(this, SignupActivity::class.java)

        AlertDialog.Builder(this).run {
            setTitle("title")
            setIcon(R.drawable.btn_submit2)
            setMessage("message")
            setPositiveButton("Yes") { _, _ ->

            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                show()
            }
        }

        binding.btnLoginSubmit.setOnClickListener(OnSingleClickListener { startActivity(loginIntent) })
        binding.btnSignupSubmit.setOnClickListener(OnSingleClickListener { startActivity(signupIntent) })
    }
}