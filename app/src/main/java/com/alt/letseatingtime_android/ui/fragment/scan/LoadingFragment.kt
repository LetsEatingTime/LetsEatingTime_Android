package com.alt.letseatingtime_android.ui.fragment.scan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentLoadingBinding
import com.alt.letseatingtime_android.ui.activity.HomeActivity
import com.alt.letseatingtime_android.ui.viewmodel.ScanViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<ScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)

        val loadingAnimation: ImageView = binding.loadingAnimation
        val gifImage = DrawableImageViewTarget(loadingAnimation)

        Glide.with(this)
            .load(R.raw.loading_animation)
            .into(gifImage)
        lifecycleScope.launch (Dispatchers.IO) {
            runBlocking {
                val scan = async {
                    viewModel.scanningMeal(){
                        val intent = Intent(requireActivity(), HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        requireActivity().finish()
                    }
                }
                scan.await()
            }
            Log.d("test", "run1")

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
