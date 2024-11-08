package com.alt.letseatingtime_android.ui.fragment.pwchange

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentChangePasswordBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.pwchange.PwChangeRequest
import com.alt.letseatingtime_android.util.LoginPattern
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        with(binding) {
            btnSubmit.setOnClickListener(OnSingleClickListener {

                val id = binding.etId.text.toString()
                val pwPattern = LoginPattern.pw.toRegex()
                val idPattern = LoginPattern.id.toRegex()
                val currentPassword = binding.etCurrentPw.text.toString()
                val newPassword = binding.etNewPw.text.toString()


                if ((id.isNotBlank() && currentPassword.isNotBlank()) && newPassword.isNotBlank()) {
                    if ((idPattern.matches(id) && pwPattern.matches(currentPassword)) && pwPattern.matches(newPassword)) {
                        pwChange(
                            PwChangeRequest(
                                id =  id,
                                password = currentPassword,
                                newPassword =  newPassword
                            )
                        )
                    } else {
                        context?.shortToast("확실하게 입력한지 확인 해 주세요.")
                    }
                } else {
                    context?.shortToast("내용을 전부 입력 해 주세요")
                }
            })
        }
        return binding.root
    }

    private fun pwChange(pwChangeRequest: PwChangeRequest){
        RetrofitClient.api.pwChange(
            pwChangeRequest
        ).enqueue(object : Callback<Unit> {

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    findNavController().navigate(R.id.action_changePasswordFragment_to_loginFragment)
                    Log.d("상태",pwChangeRequest.toString())
                    Toast.makeText(context, "성공적으로 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "회원가입 실패 다시 시도해주세요", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.e("server error", t.stackTraceToString())
                Toast.makeText(context, "서버 애러", Toast.LENGTH_SHORT).show()
            }

        })
    }


}