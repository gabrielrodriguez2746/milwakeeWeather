package com.milwaukee.weather.landing.activities

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.milwaukee.weather.landing.R
import com.milwaukee.weather.landing.databinding.ActivityLandingBinding
import dagger.android.AndroidInjection

class LandingActivity : AppCompatActivity() {

    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityLandingBinding>(this, R.layout.activity_landing)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding
    }

}