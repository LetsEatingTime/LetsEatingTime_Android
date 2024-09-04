package com.alt.letseatingtime_android.ui.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileBinding
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.ui.activity.LoginActivity
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.clvModify.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_profileModifyFragment2)
        }


        binding.clvLogout.setOnClickListener (OnSingleClickListener{
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 로그아웃하시겠습니까?")
                .setNegativeButton("취소"){ dialog, which ->
                }
                .setPositiveButton("계속"){ dialog, which ->
                    MyApplication.prefs.remove()
                    requireActivity().finish()
                }
                .show()
        })

        binding.clvResign.setOnClickListener (OnSingleClickListener{
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 회원탈퇴를 하시겠습니까?")
                .setNegativeButton("취소"){ dialog, which ->
                }
                .setPositiveButton("계속"){ dialog, which ->
                    withdraw()
                }
                .show()
        })

        binding.clvPolicy.setOnClickListener (OnSingleClickListener{
            //TODO : 정책화면으로 이동
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            intent.setPackage("com.android.chrome")
//            intent.data = Uri.parse(url)
//            context?.startActivity(intent)
            context?.shortToast("정책화면으로 이동")
        })

        return binding.root
    }


    private fun withdraw() {
        RetrofitClient.api.withdraw(
            "Bearer " + MyApplication.prefs.refreshToken
        ).enqueue(object : Callback<WithdrawResponse> {
            override fun onResponse(
                call: Call<WithdrawResponse>,
                response: Response<WithdrawResponse>
            ) {
                if (response.isSuccessful) {
                    context?.shortToast("정상적으로 회원탈퇴 되셨습니다.")
                    requireActivity().finish()
                }
                else{
                    context?.shortToast("회원탈퇴 실패 다시 시도해 주세요.")
                }
            }

            override fun onFailure(call: Call<WithdrawResponse>, t: Throwable) {
                Log.e("server error", t.stackTraceToString())
                context?.shortToast("서버 에러")
//                Toast.makeText(context, "서버 애러", Toast.LENGTH_SHORT).show()
            }

        })
    }
}