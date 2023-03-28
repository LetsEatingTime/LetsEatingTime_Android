package com.alt.letseatingtime_android

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime_android.databinding.Signup1Binding

class SignupFragment1 : Fragment() {
    companion object {
        const val TAG: String = "로그"
        fun newInstance(): SignupFragment1 {
            return SignupFragment1()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = Signup1Binding.inflate(inflater, container, false)
        val signupFragment2 = SignupFragment2()

        Log.d(TAG, "Sign1 - onCreateView() called")

        binding.btnSubmit.setOnClickListener{ replaceFragment(signupFragment2) }

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