package com.alt.letseatingtime_android

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime_android.databinding.Signup1Binding
import com.alt.letseatingtime_android.databinding.Signup2Binding

class SignupFragment2 : Fragment() {
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
    ): View? {
        val binding = Signup2Binding.inflate(inflater, container, false)
        Log.d(TAG, "Sign2 - onCreateView() called")
        return binding.root
    }
}