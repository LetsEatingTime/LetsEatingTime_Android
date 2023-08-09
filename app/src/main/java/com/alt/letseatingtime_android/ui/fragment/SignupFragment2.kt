package com.alt.letseatingtime_android.ui.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.util.LoginPattern
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.Signup2Binding
import java.util.regex.Pattern

class SignupFragment2 : Fragment() {
    private lateinit var binding: Signup2Binding

    companion object {
        const val TAG: String = "로그"
        fun newInstance(): SignupFragment2 {
            return SignupFragment2()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Signup2Binding.inflate(inflater, container, false)
        val signupFragment3 = SignupFragment3()
        val pattern = Pattern.compile(LoginPattern.pw)
        Log.d(TAG, "Sign2 - onCreateView() called")
        binding.btnSubmit.setOnClickListener {
            val pw = binding.etPw.text.toString()
            if (pw != "") {
                MyApplication.prefs.userPassword = pw
                replaceFragment(signupFragment3)
            } else {
                Toast.makeText(activity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
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