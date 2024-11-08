package com.alt.letseatingtime_android.ui.fragment.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileModifyBinding
import com.alt.letseatingtime_android.network.retrofit.response.profile.User
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.setOnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileModifyFragment : Fragment() {
    var _binding: FragmentProfileModifyBinding? = null
    val binding get() = _binding!!
    private val userViewModel by activityViewModels<UserActivityViewModel>()

    private lateinit var getImage: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback { uri ->
                if (uri == null) {
                    requireContext().shortToast("이미지가 선택되지 않았습니다")
                } else {
                    userViewModel.setImageUri(uri)
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileModifyBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)

        userViewModel.userData.observe(viewLifecycleOwner) {
            with(binding) {
                etModifyNumber.setText(it.data.user.classNo.toString())
                etModifyName.setText(it.data.user.name.toString())
                etModifyClass.setText(it.data.user.className.toString())
                etModifyGrade.setText(it.data.user.grade.toString())
            }
        }

        userViewModel.userImageUrl.observe(viewLifecycleOwner) {
            binding.ivProfileImg.load(
                if (!(it.isNullOrEmpty() || it != "")) {
                    it
                } else {
                    R.drawable.img_sample_profile
                }
            )
        }

        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.setOnClickListener {
                with(binding) {
                    if (etModifyName.text.isNotBlank() && etModifyNumber.text.isNotBlank() && etModifyClass.text.isNotBlank() && etModifyGrade.text.isNotEmpty()) {
                        userViewModel.userModify(
                            User(
                                idx = userViewModel.userData.value?.data?.user?.idx ?: -1,
                                id = userViewModel.userData.value?.data?.user?.id ?: "null",
                                name = etModifyName.text.toString(),
                                classNo = etModifyNumber.text.toString().toInt(),
                                className = etModifyClass.text.toString().toInt(),
                                grade = etModifyGrade.text.toString().toInt()
                            )
                        )
                    }
                }
            }
        }

        binding.btnProfileCancel.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 돌아가시겠습니까?\n진행상황은 저장되지 않습니다.")
                .setNegativeButton("취소") { dialog, which ->
                }
                .setPositiveButton("계속") { dialog, which ->
                    moveProfile()
                }
                .show()
        }

        userViewModel.isSuccessChange.observe(viewLifecycleOwner){
            if(it){
                moveProfile()
                userViewModel.initIsSuccessChange()
            }
        }

        binding.tvModifyProfileImg.setOnSingleClickListener {
            getImage.launch("image/*")
        }

        userViewModel.toastMessage.observe(viewLifecycleOwner){
            if (it != ""){
                requireContext().shortToast(it)
            }
        }

        return binding.root
    }

    private fun moveProfile() {
        findNavController().navigate(R.id.action_profileModifyFragment2_to_profileFragment2)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}