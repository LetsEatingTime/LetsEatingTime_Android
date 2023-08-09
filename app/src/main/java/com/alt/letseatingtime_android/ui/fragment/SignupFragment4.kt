package com.alt.letseatingtime_android.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import com.alt.letseatingtime_android.ui.activity.LoginActivity
import com.alt.letseatingtime_android.util.LoginPattern
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.Signup4Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupFragment4 : Fragment() {
    private lateinit var binding: Signup4Binding

    companion object {
        const val TAG: String = "로그"
        fun newInstance(): SignupFragment4 {
            return SignupFragment4()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Signup4Binding.inflate(inflater, container, false)
        val pattern = Pattern.compile(LoginPattern.grade)
        Log.d(TAG, "Sign4 - onCreateView() called")
        binding.btnSubmit.setOnClickListener {
            Log.d(TAG, "${binding.etGrade.text} - onCreateView() called")
            val grade = binding.etGrade.text.toString()
            val classname = binding.etClass.text.toString()
            val classno = binding.etNumber.text.toString()
            if (pattern.matcher(grade).find()) {
                if (grade != "" && classname != "" && classno != "") {
                    MyApplication.prefs.userGrade = binding.etGrade.text.toString()
                    MyApplication.prefs.userClassName = binding.etClass.text.toString()
                    MyApplication.prefs.userClassNo = binding.etNumber.text.toString()
                    signup()
                } else {
                    Toast.makeText(activity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "숫자로 입력해주세요 ex)2", Toast.LENGTH_SHORT).show()
            }
//            activity?.let{
//                val home = Intent(context, HomeActivity::class.java)
//                startActivity(home)
//            }
        }

        return binding.root
    }

    private fun signup() {
        activity?.let {
            val home = Intent(context, LoginActivity::class.java)
            startActivity(home)
        }
        MyApplication.prefs.userGrade = binding.etGrade.text.toString()
        MyApplication.prefs.userClassName = binding.etClass.text.toString()
        MyApplication.prefs.userClassNo = binding.etNumber.text.toString()
        RetrofitClient.api.signup(
            SignupRequest(
                MyApplication.prefs.userID!!,
                MyApplication.prefs.userName!!,
                MyApplication.prefs.userPassword!!,
                'S',
                MyApplication.prefs.userGrade!!,
                MyApplication.prefs.userClassName!!,
                MyApplication.prefs.userClassNo!!
            )
        ).enqueue(object :
            Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("회원가입",response.body().toString())
                } else {
                    Log.d("회원가입", response.code().toString())
                    Log.d("회원가입", SignupRequest(
                        MyApplication.prefs.userID!!,
                        MyApplication.prefs.userName!!,
                        MyApplication.prefs.userPassword!!,
                        'S',
                        MyApplication.prefs.userGrade!!,
                        MyApplication.prefs.userClassName!!,
                        MyApplication.prefs.userClassNo!!
                    ).toString())
                }

            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.d("상태", t.message.toString())
            }

        })
    }

    private fun replaceFragment(fragment: Fragment) {

        // 현 Activity 에 연결된 Fragment 관리하는 supportFragmentManager 를 통해 Fragment 전환
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()

        }
    }
}