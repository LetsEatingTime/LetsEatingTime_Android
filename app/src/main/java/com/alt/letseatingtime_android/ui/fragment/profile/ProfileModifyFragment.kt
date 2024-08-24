package com.alt.letseatingtime_android.ui.fragment.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentProfileModifyBinding

class ProfileModifyFragment : Fragment() {
    var _binding : FragmentProfileModifyBinding? = null
    val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding =FragmentProfileModifyBinding.inflate(inflater, container, false)
        binding.ibBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
        }
        binding.btnSubmit.setOnClickListener{
            findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
        }
        binding.btnProfileCancel.setOnClickListener{
            //TODO : 진짜로 돌아가시겠습니까? dialog 추가하기.
            findNavController().navigate(R.id.action_profileModifyFragment_to_profileFragment)
        }
        return binding.root
    }
}