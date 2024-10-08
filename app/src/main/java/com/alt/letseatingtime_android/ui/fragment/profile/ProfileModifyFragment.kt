package com.alt.letseatingtime_android.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileModifyBinding
import com.alt.letseatingtime_android.util.BottomController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
class ProfileModifyFragment : Fragment() {
    var _binding: FragmentProfileModifyBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileModifyBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        binding.btnSubmit.setOnClickListener {
            binding.btnSubmit.setOnClickListener {
                with(binding) {
                    if (etModifyName.text.isNotBlank() && etModifyNumber.text.isNotBlank() && etModifyClass.text.isNotBlank() && etModifyGrade.text.isNotEmpty()) {
                        // TODO : 서버 보내기.
                        moveProfile()
                    }
                }
            }
        }
        binding.btnProfileCancel.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setMessage("정말로 돌아가시겠습니까?\n진행상황은 저장되지 않습니다.")
                .setNegativeButton("취소"){ dialog, which ->
                }
                .setPositiveButton("계속"){ dialog, which ->
                    moveProfile()
                }
                .show()
//            findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
        }
        return binding.root
    }

    private fun moveProfile(){
        findNavController().navigate(R.id.action_profileModifyFragment2_to_profileFragment2)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}