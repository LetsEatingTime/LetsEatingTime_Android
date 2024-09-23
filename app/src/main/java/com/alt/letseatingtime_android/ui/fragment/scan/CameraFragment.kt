package com.alt.letseatingtime_android.ui.fragment.scan

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.Camera
import androidx.camera.core.CameraControl
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentCameraBinding
import com.alt.letseatingtime_android.ui.viewmodel.ScanViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.setOnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var camera: Camera? = null
    private var cameraController: CameraControl? = null
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private val viewModel by activityViewModels<ScanViewModel>()
    private lateinit var getImage: ActivityResultLauncher<String>


    private fun getOutputMediaFile(): File? {
        val mediaStorageDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            ), "LET"
        )

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val mediaFile = File(mediaStorageDir.path + File.separator + "IMG_${timeStamp}.jpg")
        return mediaFile
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder()
                .setTargetRotation(Surface.ROTATION_90)  // 항상 가로 모드로 설정
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )
                cameraController = camera!!.cameraControl
                cameraController!!.setZoomRatio(1F)
            } catch (exc: Exception) {
                Log.e("CameraFragment", "카메라 초기화 중 에러 발생: $exc")
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val photoFile = getOutputMediaFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile!!).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraFragment", "사진 저장 실패: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)

                    viewModel.setUri(savedUri)
                    val msg = "사진이 저장되었습니다: $savedUri"
                    findNavController().navigate(R.id.action_cameraFragment_to_imageResultFragment)
                    Log.d("CameraFragment", msg)
                }
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        getImage = registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback{
                    uri ->
                if (uri == null){
                    requireContext().shortToast("이미지가 선택되지 않았습니다")
                }
                else{
                    viewModel.setUri(uri)
                    findNavController().navigate(R.id.action_cameraFragment_to_imageResultFragment)
                }
            }
        )
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        (requireActivity() as BottomController).setBottomNavVisibility(false)

        startCamera()

        binding.clvBack.setOnClickListener {
            findNavController().navigate(R.id.action_cameraFragment_to_scanFragment)
        }

        binding.cameraCaptureButton.setOnSingleClickListener {
            takePhoto()  // 사진 촬영 기능 추가
        }

        binding.clvGallery.setOnSingleClickListener {
            getImage.launch("image/*")
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        cameraExecutor.shutdown()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
