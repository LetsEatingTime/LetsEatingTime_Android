package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alt.letseatingtime.databinding.ActivityHomeBinding
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.bumptech.glide.Glide
import com.alt.letseatingtime.R
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient.api
import com.alt.letseatingtime_android.ui.fragment.home.HomeFragment
import com.alt.letseatingtime_android.ui.fragment.profile.ProfileFragment
import com.alt.letseatingtime_android.ui.fragment.store.StoreFragment
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class HomeActivity : AppCompatActivity(), BottomController {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var bottomNavigationView: BottomNavigationView



    private val bottomNavFragments = listOf(
        StoreFragment(),
        HomeFragment(),
        ProfileFragment()
    )

    private var recentPosition = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bottomTabBar()
    }

    private fun bottomTabBar() {
        bottomNavigationView = binding.bottomNav

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.home_frame_container) as NavHostFragment
        val navView: BottomNavigationView = binding.bottomNav
        navView.setupWithNavController(navHostFragment.navController)


//        supportFragmentManager.beginTransaction()
//            .replace(binding.homeFrameContainer.id, bottomNavFragments[1])
//            .commit()
//
//        bottomNavigationView.selectedItemId = R.id.homeFragment2
//
//        bottomNavigationView.setOnItemSelectedListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            when (it.itemId) {
//                R.id.storeFragment2 -> {
//                    transaction.setCustomAnimations(
//                        R.anim.anim_slide_in_from_left_fade_in,
//                        R.anim.anim_fade_out_200
//                    )
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[0])
//                    transaction.commit()
//                    recentPosition = 0
//                    return@setOnItemSelectedListener true
//                }
//                R.id.homeFragment2 -> {
//                    if (recentPosition < 1) {
//                        transaction.setCustomAnimations(
//                            R.anim.anim_slide_in_from_right_fade_in,
//                            R.anim.anim_fade_out_200
//                        )
//                    } else {
//                        transaction.setCustomAnimations(
//                            R.anim.anim_slide_in_from_left_fade_in,
//                            R.anim.anim_fade_out_200
//                        )
//                    }
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[1])
//                    transaction.commit()
//                    recentPosition = 1
//                    return@setOnItemSelectedListener true
//                }
//                R.id.profileFragment2 -> {
//                    transaction.setCustomAnimations(
//                        R.anim.anim_slide_in_from_right_fade_in,
//                        R.anim.anim_fade_out_200
//                    )
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[2])
//                    transaction.commit()
//                    recentPosition = 4
//                    return@setOnItemSelectedListener true
//                }
//            }
//            return@setOnItemSelectedListener false
//        }
    }

    override fun setBottomNavVisibility(visibility: Boolean) {
        if(visibility){
            binding.bottomNav.visibility = View.VISIBLE
            binding.bottomLine.visibility = View.VISIBLE
        }
        else{
            binding.bottomNav.visibility = View.GONE
            binding.bottomLine.visibility = View.GONE
        }
    }

}
