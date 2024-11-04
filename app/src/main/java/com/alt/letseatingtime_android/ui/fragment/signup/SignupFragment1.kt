package com.alt.letseatingtime_android.ui.fragment.signup

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.Signup1Binding
import com.alt.letseatingtime_android.ui.activity.LoginActivity
import com.alt.letseatingtime_android.ui.viewmodel.SignupViewModel
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import java.util.regex.Pattern

class SignupFragment1 : Fragment() {
    companion object {
        fun newInstance(): SignupFragment1 {
            return SignupFragment1()
        }
    }

    private val signupViewModel by activityViewModels<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Signup1Binding.inflate(inflater, container, false)
        val signupFragment2 = SignupFragment2()

        val pattern = Pattern.compile(LoginPattern.id)


        binding.tvToLogin.setOnClickListener {
            requireActivity().let {
                Intent(context, LoginActivity::class.java).also { intent ->
                    startActivity(intent)
                }
                it.finish()
            }
        }

        binding.btnDuplicateCheck.setOnClickListener {
            signupViewModel.idDuplicateCheck(binding.etId.text.toString())
        }

        signupViewModel.message.observe(viewLifecycleOwner) {
            if (it != "") {
                val color = ContextCompat.getColor(
                    requireContext(), if (it == "중복되지 않은 아이디입니다.") {
                        R.color.good

                    } else {
                        R.color.warning
                    }
                )
                binding.tvResult.setTextColor(color)
            }
            binding.tvResult.text = it
        }

        binding.btnSubmit.setOnClickListener(OnSingleClickListener {
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
                Toast.makeText(activity, "아이디에는 영어와 숫자만 들어갈수 있습니다", Toast.LENGTH_SHORT).show()
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