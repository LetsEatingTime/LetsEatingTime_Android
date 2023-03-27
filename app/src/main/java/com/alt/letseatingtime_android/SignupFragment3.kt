package com.alt.letseatingtime_android

import androidx.fragment.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime_android.databinding.Signup1Binding
import com.alt.letseatingtime_android.databinding.Signup3Binding

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
        return binding.root
    }
}