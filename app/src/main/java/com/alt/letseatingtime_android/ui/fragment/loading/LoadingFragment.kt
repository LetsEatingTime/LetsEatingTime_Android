package com.alt.letseatingtime_android.ui.fragment.loading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentLoadingBinding
import com.alt.letseatingtime_android.ui.fragment.home.HomeFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

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

        // 5초 후에 이전 프래그먼트로 돌아가도록 설정
        Handler(Looper.getMainLooper()).postDelayed({
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.home_frame_container, HomeFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }, 5000) // 5초 동안 로딩 애니메이션을 보여줌

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
