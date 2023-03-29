package com.alt.letseatingtime_android

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime_android.databinding.Signup3Binding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupFragment3 : Fragment() {
    companion object {
        const val TAG: String = "로그"
        fun newInstance(): SignupFragment3 {
            return SignupFragment3()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = Signup3Binding.inflate(inflater, container, false)

        Log.d(TAG, "Sign3 - onCreateView() called")

        binding.btnSubmit.setOnClickListener {
            MyApplication.prefs.userName = binding.etName.text.toString()
            RetrofitClient.api.signup(SignupRequest(MyApplication.prefs.userName!!, MyApplication.prefs.userPassword!!,"2318","00:00:00","Y",'S')).enqueue(object :
                Callback<SignupResponse> {
                override fun onResponse(
                    call: Call<SignupResponse>,
                    response: Response<SignupResponse>
                ) {
                    if(response.code() != 200){
                        Log.d("상태",response.code().toString())
                    } else {
                        Log.d("상태",response.code().toString())
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Log.d("상태",t.message.toString())
                }

            })
            Log.d(TAG, "${MyApplication.prefs.userSchoolNumber.toString()} ${MyApplication.prefs.userPassword.toString()} ${MyApplication.prefs.userName.toString()}")
        }
        return binding.root
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