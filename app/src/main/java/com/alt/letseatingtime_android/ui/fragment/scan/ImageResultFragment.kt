package com.alt.letseatingtime_android.ui.fragment.scan

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentImageResultBinding
import com.alt.letseatingtime_android.ui.viewmodel.ScanViewModel
import com.alt.letseatingtime_android.util.BottomController


class ImageResultFragment : Fragment() {

    private var _binding  : FragmentImageResultBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageResultBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding.ivResult.load(viewModel.imageData.value)

        binding.tvReScan.setOnClickListener {
            findNavController().navigate(R.id.action_imageResultFragment_to_cameraFragment)
        }

        binding.tvUse.setOnClickListener {
            viewModel.scanningMeal()

            findNavController().navigate(R.id.action_imageResultFragment_to_homeFragment2)
        }

        return binding.root
    }
}