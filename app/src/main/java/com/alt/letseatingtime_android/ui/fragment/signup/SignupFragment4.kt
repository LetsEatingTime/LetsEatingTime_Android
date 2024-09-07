package com.alt.letseatingtime_android.ui.fragment.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import com.alt.letseatingtime_android.ui.activity.LoginActivity
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime.databinding.Signup4Binding
import com.alt.letseatingtime_android.util.OnSingleClickListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupFragment4 : Fragment() {
    private lateinit var binding: Signup4Binding

    private val patternGrade = Pattern.compile(LoginPattern.grade)
    private val patternClassname = Pattern.compile(LoginPattern.classname)
    private val patternClassNo = Pattern.compile(LoginPattern.classNo)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = Signup4Binding.inflate(inflater, container, false)
        val id = arguments?.getString("id").toString()
        val pw = arguments?.getString("pw").toString()
        val name = arguments?.getString("name").toString()


        with(binding) {
            btnSubmit.setOnClickListener(OnSingleClickListener {
                val grade = etGrade.text.toString()
                val classname = etClass.text.toString()
                val classNo = etNumber.text.toString()
                if (grade != "" && classname != "" && classNo != "") {
                    if (checkPattern(classNo, grade, classname)) {
                        signup(
                            SignupRequest(
                                id = id,
                                name = name,
                                password = pw,
                                grade = grade,
                                className = classname,
                                classNo = classNo,
                                userType = 'S'
                            )
                        )
                    } else {
                        Toast.makeText(activity, "다시 확인해주세요", Toast.LENGTH_SHORT).show()
                        Log.d("상태", "${grade}학년 ${classname}반 ${classNo}번 ")
                    }
                } else {
                    Toast.makeText(activity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            })
            tvToLogin.setOnClickListener {
                requireActivity().let {
                    Intent(context, LoginActivity::class.java).also { intent ->
                        startActivity(intent)
                    }
                    it.finish()
                }
            }
        }
        return binding.root
    }

    private fun signup(signupRequest: SignupRequest) {
        RetrofitClient.api.signup(
            signupRequest
        ).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>, response: Response<SignupResponse>
            ) {
                if (response.isSuccessful) {
                    requireActivity().let {
                        Intent(context, LoginActivity::class.java).also { intent ->
                            startActivity(intent)
                        }
                        it.finish()
                    }
                    Log.d("상태",signupRequest.toString())
                    Toast.makeText(context, "회원가입 대기", Toast.LENGTH_SHORT).show()
                } else {
                    requireActivity().let {
                        Intent(context, LoginActivity::class.java).also { intent ->
                            startActivity(intent)
                        }
                        it.finish()
                    }
                    Toast.makeText(context, "회원가입 실패 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.e("server error", t.stackTraceToString())
                Toast.makeText(context, "서버 애러", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun checkPattern(classNo: String, grade: String, classname: String): Boolean {
        return patternClassNo.matcher(classNo).find() && patternGrade.matcher(grade)
            .find() && patternClassname.matcher(classname)
            .find() && classNo.toInt() > 0 && classNo.toInt() <= 19
    }
}