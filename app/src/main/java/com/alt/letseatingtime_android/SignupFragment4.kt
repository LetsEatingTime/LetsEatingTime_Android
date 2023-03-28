package com.alt.letseatingtime_android

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime_android.databinding.Signup1Binding
import com.alt.letseatingtime_android.databinding.Signup4Binding

class SignupFragment4 : Fragment() {
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
    ): View? {
        val binding = Signup4Binding.inflate(inflater, container, false)
        Log.d(TAG, "Sign4 - onCreateView() called")
        return binding.root
    }

    private fun replaceFragment(fragment: Fragment) {
        // 현 Activity 에 연결된 Fragment 관리하는 supportFragmentManager 를 통해 Fragment 전환
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}