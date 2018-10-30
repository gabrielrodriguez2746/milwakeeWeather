package com.milwaukee.weather.landing.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.milwaukee.weather.base.permissions.ActivityPermissionWrapper
import com.milwaukee.weather.base.permissions.MilwaukeeActivityPermissionWrapper
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.landing.controllers.LandingController
import com.milwaukee.weather.landing.databinding.ActivityLandingBinding
import dagger.android.AndroidInjection
import javax.inject.Inject

@Suppress("DELEGATED_MEMBER_HIDES_SUPERTYPE_OVERRIDE")
class LandingActivity : AppCompatActivity(), ActivityPermissionWrapper by MilwaukeeActivityPermissionWrapper() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLandingBinding>(this, R.layout.activity_landing)
    }

    @Inject
    lateinit var controller: LandingController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding
        controller.getUserLocation().subscribe({}, {})
    }

}