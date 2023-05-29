package com.alt.letseatingtime_android.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.example.letseatingtime.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }



    private val REQUEST_CODE = 1
    private lateinit var profileImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        profileImageView = binding.studentImage
        val addPhotoButton = binding.profileEdit

        addPhotoButton.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, REQUEST_CODE)
            } else {
                openGallery()
            }
        }

        val stname = binding.someId
        val userName = prefs.userName // userName 값을 가져옵니다.
        stname.text = userName // TextView에 userName 값을 설정합니다.

        val textView = binding.trashId
        val userClass =
            prefs.userGrade + "학년 " + prefs.userClassName + "반 " + prefs.userClassNo + "번"
        textView.text = userClass


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        RetrofitClient.api.meal(current.format(formatter))
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: retrofit2.Response<MealResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("밥", "${response.body()}")
                    } else {
                        Log.d("밥", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.d("밥", t.message.toString())
                }
            })

        //로그아웃
        binding.logout.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            val imageUri = data.data
            profileImageView.setImageURI(imageUri)
        }
    }

}


