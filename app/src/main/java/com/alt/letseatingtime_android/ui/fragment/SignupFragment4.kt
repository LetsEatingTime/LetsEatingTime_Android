package com.alt.letseatingtime_android.ui.fragment

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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignupFragment4 : Fragment() {
    private lateinit var binding: Signup4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Signup4Binding.inflate(inflater, container, false)
        val pattern = Pattern.compile(LoginPattern.grade)
        val id = arguments?.getString("id").toString()
        val pw = arguments?.getString("pw").toString()
        val name = arguments?.getString("name").toString()
        binding.btnSubmit.setOnClickListener {
            val grade = binding.etGrade.text.toString()
            val classname = binding.etClass.text.toString()
            val classNo = binding.etNumber.text.toString()
            if (pattern.matcher(grade).find()) {
                if (grade != "" && classname != "" && classNo != "") {
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
                    Toast.makeText(activity, "모두 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "숫자로 입력해주세요 ex)2", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun signup(signupRequest: SignupRequest) {
        RetrofitClient.api.signup(
            signupRequest
        ).enqueue(object :
            Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if (response.isSuccessful) {
                    requireActivity().let {
                        Intent(context, LoginActivity::class.java).also {
                            startActivity(it)
                        }
                        it.finish()
                    }
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.d("상태", t.message.toString())
            }

        })
    }
}