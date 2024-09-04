package com.alt.letseatingtime_android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alt.letseatingtime.databinding.ActivityHomeBinding
import com.alt.letseatingtime.R
import com.alt.letseatingtime_android.ui.fragment.home.HomeFragment
import com.alt.letseatingtime_android.ui.fragment.profile.ProfileFragment
import com.alt.letseatingtime_android.ui.fragment.store.StoreFragment
import com.alt.letseatingtime_android.util.BottomControllable
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), BottomControllable {
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

        supportFragmentManager.beginTransaction()
            .replace(binding.homeFrameContainer.id, bottomNavFragments[1])
            .commit()

        bottomNavigationView.selectedItemId = R.id.homeFragment

        bottomNavigationView.setOnItemSelectedListener {
            val transaction = supportFragmentManager.beginTransaction()
            when (it.itemId) {
                R.id.storeFragment -> {
                    transaction.setCustomAnimations(
                        R.anim.anim_slide_in_from_left_fade_in,
                        R.anim.anim_fade_out_200
                    )
                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[0])
                    transaction.commit()
                    recentPosition = 0
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment -> {
                    if (recentPosition < 1) {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_right_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    } else {
                        transaction.setCustomAnimations(
                            R.anim.anim_slide_in_from_left_fade_in,
                            R.anim.anim_fade_out_200
                        )
                    }
                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[1])
                    transaction.commit()
                    recentPosition = 1
                    return@setOnItemSelectedListener true
                }
                R.id.profileFragment -> {
                    transaction.setCustomAnimations(
                        R.anim.anim_slide_in_from_right_fade_in,
                        R.anim.anim_fade_out_200
                    )
                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[2])
                    transaction.commit()
                    recentPosition = 4
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
    }

    override fun setBottomNavVisibility(visibility: Boolean) {
        if (visibility){
            binding.bottomNav.visibility = View.VISIBLE
            binding.bottomLine.visibility = View.VISIBLE
        }
        else{
            binding.bottomNav.visibility = View.GONE
            binding.bottomLine.visibility = View.GONE
        }
    }

}
