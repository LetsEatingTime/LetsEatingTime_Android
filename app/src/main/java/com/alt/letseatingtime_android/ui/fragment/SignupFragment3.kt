package com.alt.letseatingtime_android.ui.fragment

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
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
import com.example.letseatingtime.databinding.Signup3Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignupFragment3 : Fragment() {
    private lateinit var binding: Signup3Binding

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
    ): View {
        binding = Signup3Binding.inflate(inflater, container, false)
        val signupFragment4 = SignupFragment4()
        val pattern = Pattern.compile(LoginPattern.name)
        Log.d(TAG, "Sign3 - onCreateView() called")
        binding.btnSubmit.setOnClickListener {
            Log.d(TAG, "${binding.etName.text} - onCreateView() called")
            val name = binding.etName.text.toString()
            if (pattern.matcher(name).find()) {
                if (name != "") {
                    MyApplication.prefs.userName = name
                    replaceFragment(signupFragment4)
                } else {
                    Toast.makeText(activity, "이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "한글로 입력해주세요", Toast.LENGTH_SHORT).show()
            }
//            activity?.let{
//                val home = Intent(context, HomeActivity::class.java)
//                startActivity(home)
//            }
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