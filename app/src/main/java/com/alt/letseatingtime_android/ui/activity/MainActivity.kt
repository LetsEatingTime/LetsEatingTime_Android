package com.alt.letseatingtime_android.ui.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.MealResponse
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("상태","onCreate()")
        setContentView(binding.root)
        RetrofitClient.apiMeal.meal("20230515").enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: retrofit2.Response<MealResponse>) {
                if(response.code() == 200){
                    Log.d("상태","${response.body()}")
                } else {
                    Log.d("상태","${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }
        })

        val loginIntent = Intent(this, LoginActivity::class.java)
        val signupIntent = Intent(this, SignupActivity::class.java)
        val homeIntent = Intent(this, HomeActivity::class.java)

        // 생성된 Toast 메시지에 콜백(callback)을 추가합니다. 이 콜백은 Toast 메시지가 화면에서 보여지거나 숨겨지는 시점 등의 이벤트가 발생할 때 호출됩니다.
        Toast.makeText(this,"euya",Toast.LENGTH_SHORT).show()

//        DatePickerDialog(this,
//            { p0, p1, p2, p3 -> Log.d(TAG, "year: $p1, month: ${p2 + 1}, day: $p3") }, 2023, 4, 5).show()
//        TimePickerDialog(this,
//            { p0, p1, p2 -> Log.d(TAG, "h: $p1 m: $p2") }, 2023, 4, true).show()
        AlertDialog.Builder(this).run {
            setTitle("title")
            setIcon(R.drawable.btn_submit)
            setMessage("message")
            setPositiveButton("Yes") { dialog, which ->
            }
            setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
                show()
            }
        }

        binding.btnLoginSubmit.setOnClickListener { startActivity(loginIntent) }
        binding.btnSignupSubmit.setOnClickListener { startActivity(signupIntent) }
    }

    override fun onStart() {
        super.onStart()
        Log.d("상태","onStart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("상태","onStop()")
    }

    override fun onRestart(){
        super.onRestart()
        Log.d("상태","onRestart()")
    }

    //onStop()이던 상태가 완전이 제거되는 단계 / Activity가 호출하는 마지막 메소드
    override fun onDestroy() {
        super.onDestroy()
        Log.d("상태","onDestroy()")
    }
}