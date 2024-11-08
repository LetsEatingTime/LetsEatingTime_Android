package com.alt.letseatingtime_android.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ActivityHomeBinding
import com.alt.letseatingtime_android.ui.fragment.home.HomeFragment
import com.alt.letseatingtime_android.ui.fragment.profile.ProfileFragment
import com.alt.letseatingtime_android.ui.fragment.store.StoreFragment
import com.alt.letseatingtime_android.ui.viewmodel.ScanViewModel
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.shortToast
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), BottomController {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var userViewModel: UserActivityViewModel
    private lateinit var storeViewModel: StoreViewModel
    private lateinit var scanViewModel: ScanViewModel


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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_frame_container) as NavHostFragment
        val navView: BottomNavigationView = binding.bottomNav
        navView.setupWithNavController(navHostFragment.navController)

        userViewModel = ViewModelProvider(this)[UserActivityViewModel::class.java]
        storeViewModel = ViewModelProvider(this)[StoreViewModel::class.java]
        scanViewModel = ViewModelProvider(this)[ScanViewModel::class.java]

        userViewModel.toastMessage.observe(this) {
            if (it != "") {
                this.shortToast(it)
            }
        }

        storeViewModel.toastMessage.observe(this) {
            if (it != "") {
                this.shortToast(it)
            }
        }

        scanViewModel.toastMessage.observe(this) {
            if (it != "") {
                this.shortToast(it)
            }
        }
    }

    override fun setBottomNavVisibility(visibility: Boolean) {
        if (visibility) {
            binding.bottomNav.visibility = View.VISIBLE
            binding.bottomLine.visibility = View.VISIBLE
        } else {
            binding.bottomNav.visibility = View.GONE
            binding.bottomLine.visibility = View.GONE
        }
    }

}
