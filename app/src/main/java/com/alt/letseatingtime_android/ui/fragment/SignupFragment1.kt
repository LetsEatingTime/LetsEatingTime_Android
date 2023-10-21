package com.alt.letseatingtime_android.ui.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.Signup1Binding
import java.util.regex.Pattern

class SignupFragment1 : Fragment() {
    companion object {
        fun newInstance(): SignupFragment1 {
            return SignupFragment1()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Signup1Binding.inflate(inflater, container, false)
        val signupFragment2 = SignupFragment2()
        val pattern = Pattern.compile(LoginPattern.id)

        binding.btnSubmit.setOnClickListener{
            val id = binding.etId.text.toString()
            if (pattern.matcher(id).find()) {
                if (id != "") {
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    replaceFragment(signupFragment2, bundle)
                } else {
                    Toast.makeText(activity, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "영문으로 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
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