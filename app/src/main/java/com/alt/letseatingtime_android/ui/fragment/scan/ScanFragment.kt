package com.alt.letseatingtime_android.ui.fragment.scan

import android.Manifest
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentScanBinding
import com.alt.letseatingtime_android.util.BottomController


class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!
    private val CAMERA_PERMISSION_CODE = 999

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT



        binding.ibBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_scanFragment_to_homeFragment2)
        }
        binding.btnScan.setOnClickListener {
            checkingPermission()
//            if((requireContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) && (requireContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ){
//                requireContext().shortToast("접근 권한이 허용되지 않아 카메라를 실행할 수 없습니다. 설정에서 접근 권한을 허용해주세요.")
//                Log.d("camera", "not in check")
//            }
//            else{
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERA_PERMISSION_CODE)
//                findNavController().navigate(R.id.action_scanFragment_to_cameraFragment)
//                Log.d("camera", "in check")
//                requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
//                (requireActivity() as BottomController).setBottomNavVisibility(false)
//
//            }

        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }




    private fun checkingPermission() {
        val permissions = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissions.add(Manifest.permission.CAMERA)
        }

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissions.toTypedArray(),
                CAMERA_PERMISSION_CODE
            )
        } else {
            // 권한이 이미 허용된 경우 사진 촬영 가능
            findNavController().navigate(R.id.action_scanFragment_to_cameraFragment)
        }
    }

}