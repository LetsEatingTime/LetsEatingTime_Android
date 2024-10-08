package com.alt.letseatingtime_android.ui.fragment.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileBinding
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.alt.letseatingtime_android.ui.viewmodel.ScanViewModel
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!

    private val userViewModel by activityViewModels<UserActivityViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        userViewModel.getProfile()

        userViewModel.userData.observe(viewLifecycleOwner) {
            binding.tvStudentName.text = "안녕하세요, ${it.data?.user?.name}님"
            when (it.data.user.userType) {
                "T" -> {
                    binding.tvStudentNumber.text = "선생님 계정입니다."
                }

                "S" -> {
                    binding.tvStudentNumber.text =
                        "${it.data.user.grade}학년 ${it.data.user.className}반 ${it.data.user.classNo}번"
                }
            }
        }


        binding.clvModify.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment2_to_profileModifyFragment2)
        }

        binding.clvLogout.setOnClickListener(OnSingleClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 로그아웃하시겠습니까?")
                .setNegativeButton("취소") { dialog, which ->
                }
                .setPositiveButton("계속") { dialog, which ->
                    MyApplication.prefs.remove()
                    requireActivity().finish()
                }
                .show()
        })

        binding.clvResign.setOnClickListener(OnSingleClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 회원탈퇴를 하시겠습니까?")
                .setNegativeButton("취소") { dialog, which ->
                }
                .setPositiveButton("계속") { dialog, which ->
                    userViewModel.withdraw(context = requireContext())
                    requireActivity().finish()
                }
                .show()
        })

        binding.clvPolicy.setOnClickListener(OnSingleClickListener {
            //TODO : 정책화면으로 이동
            context?.shortToast("정책화면으로 이동")
        })

        return binding.root
    }

    private fun initProfile() {
        RetrofitClient.api.profile("Bearer " + MyApplication.prefs.accessToken)
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>,
                    response: Response<ProfileResponse>
                ) {
                    val result = response.body()
                    if (response.isSuccessful) {
                        binding.tvStudentName.text = "안녕하세요, ${result?.data?.user?.name}님"
                        when (result?.data?.user?.userType) {
                            "T" -> {
                                binding.tvStudentNumber.text = "선생님 계정입니다."
                            }

                            "S" -> {
                                binding.tvStudentNumber.text =
                                    "${result.data.user.grade}학년 ${result.data.user.className}반 ${result.data.user.classNo}번"
                            }
                        }
                    } else {
                        Log.e(
                            "ProfileFragment",
                            "${
                                response.errorBody().toString()
                            }, ${response.code()}, ${response.body()}, ${response.message()}"
                        )
                        context?.shortToast("프로필 정보를 가져오지 못했습니다.")
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Log.e("server error", t.stackTraceToString())
                    context?.shortToast("서버 에러")
                }
            })


    }
}