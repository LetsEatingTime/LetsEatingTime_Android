package com.alt.letseatingtime_android.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentMealListBinding

class MealListFragment : Fragment() {

    private lateinit var binding: FragmentMealListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater, container, false)

        return binding.root
    }
}