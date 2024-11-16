package com.alt.letseatingtime_android.ui.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileBinding
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

        userViewModel.userImageUrl.observe(viewLifecycleOwner) {
            binding.ivStudentProfile.load(
                if (!(it.isNullOrEmpty() || it != "")) {
                    it
                } else {
                    R.drawable.img_sample_profile
                }
            )
        }

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

        userViewModel.toastMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                requireContext().shortToast(it)
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
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://encouraging-wok-aa3.notion.site/140b28899b6c804d9afcc5de19c288f0?pvs=4")
            )
            startActivity(intent)
        })

        return binding.root
    }

//    private fun initProfile() {
//        RetrofitClient.api.profile("Bearer " + MyApplication.prefs.accessToken)
//            .enqueue(object : Callback<ProfileResponse> {
//                override fun onResponse(
//                    call: Call<ProfileResponse>,
//                    response: Response<ProfileResponse>
//                ) {
//                    val result = response.body()
//                    if (response.isSuccessful) {
//                        binding.tvStudentName.text = "안녕하세요, ${result?.data?.user?.name}님"
//                        when (result?.data?.user?.userType) {
//                            "T" -> {
//                                binding.tvStudentNumber.text = "선생님 계정입니다."
//                            }
//
//                            "S" -> {
//                                binding.tvStudentNumber.text =
//                                    "${result.data.user.grade}학년 ${result.data.user.className}반 ${result.data.user.classNo}번"
//                            }
//                        }
//                    } else {
//                        Log.e(
//                            "ProfileFragment",
//                            "${
//                                response.errorBody().toString()
//                            }, ${response.code()}, ${response.body()}, ${response.message()}"
//                        )
//                        context?.shortToast("프로필 정보를 가져오지 못했습니다.")
//                    }
//                }
//
//                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                    Log.e("server error", t.stackTraceToString())
//                    context?.shortToast("서버 에러")
//                }
//            })
//
//
//    }
}