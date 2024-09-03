package com.alt.letseatingtime

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.databinding.FragmentChangePasswordBinding
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime_android.util.shortToast
import java.util.regex.Pattern

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener {
            val id = binding.etId.text.toString()
            val pwPattern = LoginPattern.pw.toRegex()
            val idPattern = LoginPattern.id.toRegex()
            val currentPassword = binding.etCurrentPw.text.toString()
            val newPassword = binding.etNewPw.text.toString()


            if (id.isNotBlank() && currentPassword.isNotBlank() && newPassword.isNotBlank()) {
                if (idPattern.matches(id) && pwPattern.matches(currentPassword) && pwPattern.matches(newPassword)
                ) {
                    findNavController().navigate(R.id.action_changePasswordFragment_to_loginFragment)
                } else {
                    context?.shortToast("확실하게 입력한지 확인 해 주세요.")
                }
            } else {
                context?.shortToast("내용을 전부 입력 해 주세요")
            }

        }
        return binding.root
    }
}