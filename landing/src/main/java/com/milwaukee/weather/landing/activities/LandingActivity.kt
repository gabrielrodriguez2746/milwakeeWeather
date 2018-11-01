package com.milwaukee.weather.landing.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.milwaukee.weather.base.permissions.PermissionWrapper
import com.milwaukee.weather.base.permissions.MilwaukeePermissionWrapper
import com.milwaukee.weather.landing.LandingViewModel
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.landing.databinding.ActivityLandingBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class LandingActivity : AppCompatActivity(), PermissionWrapper by MilwaukeePermissionWrapper() {

    @Inject
    lateinit var viewModel: LandingViewModel

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLandingBinding>(this, R.layout.activity_landing)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        lifecycle.addObserver(viewModel)
        binding.viewModel = viewModel
    }

}