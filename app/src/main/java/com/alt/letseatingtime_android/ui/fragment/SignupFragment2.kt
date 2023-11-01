package com.alt.letseatingtime_android.ui.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.Signup2Binding
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime_android.util.OnSingleClickListener
import java.util.regex.Pattern

class SignupFragment2 : Fragment() {
    private lateinit var binding: Signup2Binding

    private val signupFragment3 = SignupFragment3()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = Signup2Binding.inflate(inflater, container, false)
        val id = arguments?.getString("id").toString()
        val pattern = Pattern.compile(LoginPattern.pw)
        binding.btnSubmit.setOnClickListener(OnSingleClickListener {
            val pw = binding.etPw.text.toString()
            if(pattern.matcher(pw).find()){
                if (pw != "") {
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    bundle.putString("password", pw)
                    replaceFragment(signupFragment3, bundle)
                } else {
                    Toast.makeText(activity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "대소문자, 특수문자, 숫자만 들어갈수 있습니다", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }

    private fun replaceFragment(fragment: Fragment, bundle: Bundle) {
        fragment.arguments = bundle
        // 현 Activity 에 연결된 Fragment 관리하는 supportFragmentManager 를 통해 Fragment 전환
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }
}